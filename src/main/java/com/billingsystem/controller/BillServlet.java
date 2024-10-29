package com.billingsystem.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.billingsystem.Model.Cart;
import com.billingsystem.Model.CartItem;
import com.billingsystem.Model.Invoice;
import com.billingsystem.Model.Product;
import com.billingsystem.Model.Transactions;
import com.billingsystem.Model.User;
import com.billingsystem.service.InvoiceService;
import com.billingsystem.service.ProductService;
import com.billingsystem.service.TransactionsService;
import com.billingsystem.service.UserService;
import com.billingsystem.utility.EmailUtility;
import com.billingsystem.utility.LoggerUtil;

@WebServlet("/BillServlet")
public class BillServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductService productService = new ProductService();
	InvoiceService InvoiceService = new InvoiceService();
	TransactionsService transactionService = new TransactionsService();
	UserService userService = new UserService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user"); 
        Long cashierUserId = null;
        double totalAmount = Double.parseDouble(request.getParameter("overallAmount"));
        if(user.getRole().toString().equals("CASHIER")) {
        	LoggerUtil.getInstance().getLogger().debug("Is Cashier "+user.getId());
        	cashierUserId = user.getId();
        	user = userService.checkUser(session.getAttribute("customerPhoneNumber").toString());
        }
       
            double providedOffer = Double.parseDouble(request.getParameter("providedOffer") != null && !request.getParameter("providedOffer").isEmpty() ? request.getParameter("providedOffer") : "0");
            if(user.haveCreditForTransaction(totalAmount) || user.getRole().toString().equals("INVENTORY_MANAGER")) {
	            boolean stockAvailable = true;
	            for (CartItem item : cart.getItems()) {
	                Product product = item.getProduct();
	                int quantity = item.getQuantity();
	
	                if (product.getStockLeft() >= quantity || user.getRole().toString().equals("INVENTORY_MANAGER")) {
	                	if(user.getRole().toString().equals("INVENTORY_MANAGER")) {
	                		product.setStockLeft(product.getStockLeft() + quantity);
	                	}else {
	                		product.setStockLeft(product.getStockLeft() - quantity);
	                		product.setTotalSold(product.getTotalSold() + quantity);
	                	}
	                    productService.updateProductStock(product);
	                } else {
	                    stockAvailable = false;
	                    request.setAttribute("error", "Not enough stock for product: " + product.getName());
	                    break;
	                }
	            }

	            if (stockAvailable || user.getRole().toString().equals("INVENTORY_MANAGER")) {
	            	
	            		Transactions transaction = new Transactions();
	            		if(cashierUserId != null) {
	            			LoggerUtil.getInstance().getLogger().debug("cashierID not null "+cashierUserId);
	            			transaction.setCashier_id(cashierUserId);
	            			userService.addPointsToCashier(cashierUserId);
	            		}
	                	transaction.setCustomer_id(user.getId());
	                	transaction.setAmount(totalAmount);
	                	transaction.setTransaction_date(LocalDateTime.now());
	                	transactionService.saveTransactions(transaction);
	                	userService.makeTransaction(user, totalAmount);
	                
	                	
	            		Invoice invoice = new Invoice();
	                    invoice.setCart(cart);
	                    invoice.setTotalAmount(totalAmount);
	                    invoice.setCustomer(user);
	                    invoice.setDate(LocalDateTime.now());
	                    invoice.setTransaction_id(transaction.getTransaction_id());
	                    
	
	                    InvoiceService.saveInvoice(invoice);
	                    String[] emailConfirmation = null;
	                    try {
	                        String subject = "Your Invoice from SuperMarket";
	                        emailConfirmation = EmailUtility.sendInvoiceEmail(user.getEmail(),subject ,  invoice, providedOffer);
	                        
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	
	                    session.removeAttribute("cart");
	                    session.removeAttribute("providedOffer");
	                    session.removeAttribute("couponMessage");
	                    session.removeAttribute("customerExist");
	                    session.removeAttribute("errorMessage");
	                    session.removeAttribute("userNotExist");
	                    session.removeAttribute("errorInCreateUser");
	                    session.removeAttribute("customerExist");
	                    session.removeAttribute("appliedCouponCode");
	                    if(emailConfirmation != null) {
	                    	session.setAttribute("pdfPath", emailConfirmation[1]);
	                    	session.setAttribute("invoiceBody", emailConfirmation[0].replaceAll("\n", "<br>"));
	                    }
	                    response.sendRedirect("confirmation.jsp");
	            	}else {
	            		LoggerUtil.getInstance().getLogger().debug("Stock isn't Available");
	            		session.setAttribute("errorMessage", "Stock isn't Available, please check with that!");
	            		response.sendRedirect("products");
	            		
	            	}
            } else {
            	LoggerUtil.getInstance().getLogger().debug("Customer Doesn't have enough Credit to buy, please buy with in Rs. "+user.getCurrent_credit());
            	session.setAttribute("errorMessage", "Customer Doesn't have enough Credit to buy, please buy with in Rs. "+user.getCurrent_credit());
            	response.sendRedirect("products");
            }
       
        
    }

   
}
