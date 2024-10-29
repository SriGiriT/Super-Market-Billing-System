package com.billingsystem.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.billingsystem.Model.User;
import com.billingsystem.service.ReportService;


@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ReportService reportService = new ReportService();
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		  String action = request.getParameter("action");
		  HttpSession session = request.getSession(false);
		  String title = "";
		  User user = (User)session.getAttribute("user");
	      List<String> reportTitle = new ArrayList<>();
	      User.Role role = user.getRole();
	      String result = "";
	      switch(role) {
	      	case ADMIN:
	      		 switch (action) {
		            case "topSellingProducts":
		                result = reportService.getTopSellingProducts();
		                title = "Top Selling Products";
		                reportTitle.addAll(Arrays.asList("Product ID", "Product Name", "Total Quantity Sold"));
		                break;
		                
		            case "topCustomers":
		            	result = reportService.getTopCustomers();
		            	title = "Top Customers";
		            	reportTitle.addAll(Arrays.asList("User ID", "User Name", "Total Spent"));
		                break;

		            case "inactiveCustomers":
		            	result = reportService.getInactiveCustomers();
		            	title = "Inactive Customers";
		            	reportTitle.addAll(Arrays.asList("User ID", "User Name"));
		                break;
		            case "topCashier":
		            	result = reportService.getTopCashier();
		            	title = "Cashiers Who Billed The Most";
		            	reportTitle.addAll(Arrays.asList("Cashier ID", "Cashier Name", "Bill Count"));
		            	break;
		                
		            case "dailyRevenue":
		            	result = reportService.getDailyRevenue();
		            	title = "Daily Revenue";
		            	reportTitle.addAll(Arrays.asList("Day", "Total Revenue"));
		                break;
		                
		            case "signedUpNoPurchase":
		            	result = reportService.getSignedUpButNoPurchase();
		            	title = "Signed Up But Not Purchased";
		            	reportTitle.addAll(Arrays.asList("User ID", "User Name"));
		                break;
		                
		            case "lowStock":
		            	result = reportService.getLowStockProducts();
		            	title = "Low Stock";
		            	reportTitle.addAll(Arrays.asList("ProductID", "Product Name", "Stock Left"));
		                break;	    
		            
		            case "frequentlyBoughtItem":
		            	int userId = Integer.parseInt(request.getParameter("userId"));
		            	result = reportService.getfrequentlyBoughtItem(userId);
		            	title = "Frequently Bought Item";
		            	reportTitle.addAll(Arrays.asList("Product ID", "Product Name", "Total Quantity Bought"));
		            	break;
		            case "unsoldProducts":
		            	result = reportService.getUnsoldProducts();
		            	title = "Unsold Products";
		            	reportTitle.addAll(Arrays.asList("Product Id", "Product Name", "Stock Left", "Price"));
		            	break;
		            case "outOfStock":
		            	result = reportService.getOutOfStockProducts();
		            	title = "Unsold Products";
		            	reportTitle.addAll(Arrays.asList("Product Id", "Product Name", "Price"));
		            	break;
		            default:
		                request.setAttribute("errorMessage", "Invalid report type.");
		                request.getRequestDispatcher("reports.jsp").forward(request, response);;
		                break;
		        }
	      		break;
	      	case CUSTOMER:
	      	case INVENTORY_MANAGER:
	      	case CASHIER:
	      		 
	      		 switch (action) {
		            case "frequentlyBoughtItem":
		            	result = reportService.getfrequentlyBoughtItem(Integer.parseInt(request.getParameter("userId")));
		            	title = "Frequently Bought Item";
		            	reportTitle.addAll(Arrays.asList("Product ID", "Product Name", "Total Quantity Bought"));
		            	break;
		            case "allBillStatements":
		            	result = reportService.getAllBillStatemetns(Integer.parseInt(request.getParameter("userId")));
		            	title = "All Bill Statements";
		            	reportTitle.addAll(Arrays.asList("Total Amount", "Transaction Date", "Transaction ID"));
		            	break;
		            case "viewPurchaseDetails":
		            	result = reportService.getPurchaseDetails(Integer.parseInt(request.getParameter("transactionId")));
		            	title = "Purchase Details";
		            	reportTitle.addAll(Arrays.asList("Product ID", "Product Name", "Quantities", "Price"));
		            	break;
		            default:
		                request.setAttribute("errorMessage", "Invalid report type.");
		                request.getRequestDispatcher("reports.jsp").forward(request, response);;
		                break;
		        }
	      	
	      }
	       RequestDispatcher dispatcher = request.getRequestDispatcher("header.jsp");
	       dispatcher.include(request, response);
	        response.getWriter().println(" "
	        		+ "<!DOCTYPE html>"
	        		+ "<html>"
	        		+ "<head>"
	        		+ "    <title>Report of "+ title +"</title>"
	        		+ "<link rel=\"stylesheet\" href=\"style_home.css\">"
	        		
	        		+ "</head>");
	        try {
				response.getWriter().println("<body style=\"background-image:url('"+new URI("https://images.pexels.com/photos/264636/pexels-photo-264636.jpeg").toURL()+"');"
						+ "   	background-size: cover; backdrop-filter: blur(5px);        "
						+ "    background-position: center;   "
						+ "    background-repeat: no-repeat; color:white"
						+ "\">");
			} catch (MalformedURLException e) {
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        response.getWriter().println("<div class='content-container' style=\"background: rgba( 0, 0, 0, 0.8 ); "
	        +"box-shadow: 0 8px 32px 0 rgba( 31, 38, 135, 0.37 );"+
	        "backdrop-filter: blur( 20px );"+
	         "-webkit-backdrop-filter: blur( 20px );"+
	         "border-radius: 10px;color:white;"+
	         "border: 1px solid rgba( 255, 255, 255, 0.18 );\"><table><thead><tr>");
	        for(String eachTitle:reportTitle) {
	        	response.getWriter().println("<th>"+eachTitle+"</th>");
	        }
	        response.getWriter().println("</tr></thead><tbody>");
	        response.getWriter().println(result);	        
	        response.getWriter().println("</tbody></table></div></body></html>");
	}

	

}
