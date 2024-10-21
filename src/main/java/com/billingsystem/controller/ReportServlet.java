package com.billingsystem.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.billingsystem.service.ReportService;


@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ReportService reportService = new ReportService();
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		  String action = request.getParameter("action");
		  String title = "";
	      List<String> reportTitle = new ArrayList<>();
	      List<String> actualTitle =new ArrayList<>();
	      ResultSet resultSet = null;
	        switch (action) {
	            case "topSellingProducts":
	                 resultSet = reportService.getTopSellingProducts();
	                title = "Top Selling Products";
	                reportTitle.addAll(Arrays.asList("Product ID", "Product Name", "Total Quantity Sold"));
	                actualTitle.addAll(Arrays.asList("id", "name", "total_quantity_sold"));
	                break;
	                
	            case "topCustomers":
	            	 resultSet = reportService.getTopCustomers();
	            	title = "Top Customers";
	            	reportTitle.addAll(Arrays.asList("User ID", "User Name", "Total Spent"));
	            	actualTitle.addAll(Arrays.asList("id", "user_name", "total_spent"));
	                break;

	            case "inactiveCustomers":
	            	resultSet = reportService.getInactiveCustomers();
	            	title = "Inactive Customers";
	            	reportTitle.addAll(Arrays.asList("User ID", "User Name"));
	            	actualTitle.addAll(Arrays.asList("id", "user_name"));
	                break;
	                
	                
	            case "dailyRevenue":
	            	resultSet = reportService.getDailyRevenue();
	            	title = "Daily Revenue";
	            	reportTitle.addAll(Arrays.asList("Day", "Total Revenue"));
	            	actualTitle.addAll(Arrays.asList("day", "total_revenue"));
	                break;
	                
	            case "signedUpNoPurchase":
	            	resultSet = reportService.getSignedUpButNoPurchase();
	            	title = "Signed Up But Not Purchased";
	            	reportTitle.addAll(Arrays.asList("User ID", "User Name"));
	            	actualTitle.addAll(Arrays.asList("id", "user_name"));
	                break;
	                
	            case "lowStock":
	            	resultSet = reportService.getLowStockProducts();
	            	title = "Low Stock";
	            	reportTitle.addAll(Arrays.asList("ProductID", "Product Name", "Stock Left"));
	            	actualTitle.addAll(Arrays.asList("id", "name", "stock_left"));
	                break;
	               
	            case "frequentlyBoughtItem":
	            	int userId = Integer.parseInt(request.getParameter("userId"));
	            	resultSet = reportService.getfrequentlyBoughtItem(userId);
	            	title = "Frequently Bought Item";
	            	reportTitle.addAll(Arrays.asList("Product ID", "Product Name", "Total Quantity Bought"));
	            	actualTitle.addAll(Arrays.asList("id", "name", "total_quantity_bought"));
	            	break;
	                
	            default:
	                request.setAttribute("errorMessage", "Invalid report type.");
	                request.getRequestDispatcher("reports.jsp").forward(request, response);;
	                break;
	        }
	        response.getWriter().println(""
	        		+ "<!DOCTYPE html>"
	        		+ "<html>"
	        		+ "<head>"
	        		+ "    <title>Report of "+ title +"</title>"
	        		+ "</head>");
	        response.getWriter().println("<table><thead><tr>");
	        for(String eachTitle:reportTitle) {
	        	response.getWriter().println("<th>"+eachTitle+"</th>");
	        }
	        response.getWriter().println("</tr></thead><tbody>");
	        if(resultSet != null) {
	        	try {
	        		while(resultSet.next()) {
	        			response.getWriter().println("<tr>");
	        			for(String eachRow:actualTitle) {
	        				response.getWriter().println("<td>"+resultSet.getString(eachRow)+"</td>");
	        			}
	        			response.getWriter().println("</tr>");
		        	}
	        	}catch(SQLException e) {
	        		e.printStackTrace();
	        	}
	        }
	        
	        response.getWriter().println("</tbody></table></body></html>");
	}

	

}
