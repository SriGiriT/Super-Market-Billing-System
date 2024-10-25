package com.billingsystem.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

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
        if(user.getRole().toString().equals("CASHIER")) {
        	LoggerUtil.getInstance().getLogger().debug("Is Cashier "+user.getId());
        	cashierUserId = user.getId();
        	user = userService.checkUser(session.getAttribute("customerPhoneNumber").toString());
        }
        double totalAmount = Double.parseDouble(request.getParameter("overallAmount"));
        double providedOffer = Double.parseDouble(request.getParameter("providedOffer") != null && !request.getParameter("providedOffer").isEmpty() ? request.getParameter("providedOffer") : "0");
        if(user.haveCreditForTransaction(totalAmount)) {
        
        boolean stockAvailable = true;

        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();

            if (product.getStockLeft() >= quantity) {
                product.setStockLeft(product.getStockLeft() - quantity);
                product.setTotalSold(product.getTotalSold() + quantity);
                productService.updateProductStock(product);
            } else {
                stockAvailable = false;
                request.setAttribute("error", "Not enough stock for product: " + product.getName());
                break;
            }
        }

        if (stockAvailable) {
        	
        		Transactions transaction = new Transactions();
        		if(cashierUserId != null) {
        			LoggerUtil.getInstance().getLogger().debug("cashierID not null "+cashierUserId);
        			transaction.setCashier_id(cashierUserId);
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
                	LoggerUtil.getInstance().logException("Error in Updating invoice", e);
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
                	session.setAttribute("invoiceBody", emailConfirmation[0]);
                }
                response.sendRedirect("confirmation.jsp");
        	}else {
        		request.setAttribute("errorMessage", "Stock isn't Available, please check with that!");
        		request.getRequestDispatcher("cashier.jsp").forward(request, response);
        		
        	}
        	
        	
        	
        	
        } else {
        	request.setAttribute("errorMessage", "Customer Doesn't have enough Credit to buy, please buy with in Rs. "+user.getCurrent_credit());
    		request.getRequestDispatcher("cashier.jsp").forward(request, response);
        }
    }

   
}
