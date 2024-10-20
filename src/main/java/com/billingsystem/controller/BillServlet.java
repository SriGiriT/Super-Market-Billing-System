package com.billingsystem.controller;

import java.io.IOException;
import java.util.Date;

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
        double totalAmount = Double.parseDouble(request.getParameter("overallAmount"));
        double providedOffer = Double.parseDouble(request.getParameter("providedOffer"));
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
            	transaction.setCustomer_id(user.getId());
            	transaction.setAmount(totalAmount);
            	transaction.setTransaction_date(new Date());
            	transactionService.saveTransactions(transaction);
            	userService.assignCurrentCredit(user.getPhoneNumber(), user.getCurrent_credit()-totalAmount);
            	
        		Invoice invoice = new Invoice();
                invoice.setCart(cart);
                invoice.setTotalAmount(totalAmount);
                invoice.setCustomer(user);
                invoice.setDate(new Date());
                invoice.setTransaction_id(transaction.getTransaction_id());

                InvoiceService.saveInvoice(invoice);

                try {
                    String subject = "Your Invoice from SuperMarket";
                    EmailUtility.sendInvoiceEmail(user.getEmail(),subject ,  invoice, providedOffer);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }

                session.removeAttribute("cart");

                response.sendRedirect("confirmation.jsp");
        	}else {
        		request.getRequestDispatcher("cart.jsp").forward(request, response);
        		
        	}
        	
        	
        	
        	
        } else {
        	request.setAttribute("errorMessage", "Customer Doesn't have enough Credit to buy, please buy with in Rs. "+user.getCurrent_credit());
    		request.getRequestDispatcher("cart.jsp").forward(request, response);
        }
    }

   
}
