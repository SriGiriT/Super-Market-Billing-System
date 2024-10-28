package com.billingsystem.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.billingsystem.utility.DBConnectionPoolUtil;

public class ReportDao {
	ResultSet rs = null;
	public String getTopSessingProducts() {
		StringBuffer result = new StringBuffer();
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("SELECT p.id, p.name, SUM(ii.quantity) AS total_quantity_sold "
    					+ "FROM products p "
    					+ "JOIN invoice_items ii ON p.id = ii.product_id "
    					+ "GROUP BY p.id, p.name "
    					+ "ORDER BY total_quantity_sold DESC "
    					+ "LIMIT 10;")	) {
			 rs = ps.executeQuery();
			 while(rs.next()) {
     			result.append("<tr>");
     			for(String eachRow: new String[]{"id", "name", "total_quantity_sold"}) {
     				result.append("<td>"+rs.getString(eachRow)+"</td>");
     			}
     			result.append("</tr>");
	        	}
			 
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public String getTopCustomers() {
		StringBuffer result = new StringBuffer();
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("SELECT u.id, u.user_name, SUM(i.total_amount) AS total_spent " 
    					+ "FROM user u "
    					+ "JOIN invoices i ON u.id = i.user_id "
    					+ "GROUP BY u.id, u.user_name "
    					+ "ORDER BY total_spent DESC "
    					+ "LIMIT 10;")	) {
			
			rs = ps.executeQuery();
			 while(rs.next()) {
	     			result.append("<tr>");
	     			for(String eachRow: new String[]{"id", "user_name", "total_spent"}) {
	     				result.append("<td>"+rs.getString(eachRow)+"</td>");
	     			}
	     			result.append("</tr>");
		        	}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public String getInactiveCustomers(){
		StringBuffer result = new StringBuffer();
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("SELECT u.id, u.user_name " 
    					+ "FROM user u "
    					+ "LEFT JOIN invoices i ON u.id = i.user_id AND i.date > NOW() - INTERVAL 1 MONTH "
    					+ "WHERE i.id IS NULL "
    					+ "AND u.role = 'CUSTOMER';")	) {
			rs = ps.executeQuery();
			 while(rs.next()) {
	     			result.append("<tr>");
	     			for(String eachRow: new String[]{"id", "user_name"}) {
	     				result.append("<td>"+rs.getString(eachRow)+"</td>");
	     			}
	     			result.append("</tr>");
		        	}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	public String getDailyRevenue(){
		StringBuffer result = new StringBuffer();
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("SELECT DATE(i.date) AS day, SUM(i.total_amount) AS total_revenue "
    					+ "FROM invoices i "
    					+ "WHERE i.date >= CURDATE() - INTERVAL 7 DAY "
    					+ "GROUP BY DATE(i.date) "
    					+ "ORDER BY day DESC;")	){
			rs = ps.executeQuery();
			 while(rs.next()) {
	     			result.append("<tr>");
	     			for(String eachRow: new String[]{"day", "total_revenue"}) {
	     				result.append("<td>"+rs.getString(eachRow)+"</td>");
	     			}
	     			result.append("</tr>");
		        	}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public String getSignedUpButNoPurchase() {
		StringBuffer result = new StringBuffer();
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("SELECT u.id, u.user_name "
    					+ "FROM user u "
    					+ "LEFT JOIN invoices i ON u.id = i.user_id "
    					+ "WHERE i.id IS NULL "
    					+ "AND u.role = 'CUSTOMER';")	) {
			rs = ps.executeQuery();
			 while(rs.next()) {
	     			result.append("<tr>");
	     			for(String eachRow: new String[]{"id", "user_name"}) {
	     				result.append("<td>"+rs.getString(eachRow)+"</td>");
	     			}
	     			result.append("</tr>");
		        	}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public String getLowStockProducts() {
		StringBuffer result = new StringBuffer();
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("SELECT id, name, stock_left "
    					+ "FROM products "
    					+ "WHERE stock_left < usual_stock;")	){
			rs = ps.executeQuery();
			 while(rs.next()) {
	     			result.append("<tr>");
	     			for(String eachRow: new String[]{"id", "name", "stock_left"}) {
	     				result.append("<td>"+rs.getString(eachRow)+"</td>");
	     			}
	     			result.append("</tr>");
		        	}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public String getfrequentlyBoughtItem(int user_id) {
		StringBuffer result = new StringBuffer();
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("SELECT p.id, p.name, SUM(ii.quantity) AS total_quantity_bought "
    					+ "FROM products p "
    					+ "JOIN invoice_items ii ON p.id = ii.product_id "
    					+ "JOIN invoices i ON ii.invoice_id = i.id "
    					+ "WHERE i.user_id = ? "
    					+ "GROUP BY p.id, p.name "
    					+ "ORDER BY total_quantity_bought DESC;")	){
			ps.setInt(1, user_id);
			rs = ps.executeQuery();
			 while(rs.next()) {
	     			result.append("<tr>");
	     			for(String eachRow: new String[]{"id", "name", "total_quantity_bought"}) {
	     				result.append("<td>"+rs.getString(eachRow)+"</td>");
	     			}
	     			result.append("</tr>");
		        	}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public String getAllBillStatements(int userId) {
		StringBuffer result = new StringBuffer();
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("SELECT total_amount, date, transaction_id from invoices where user_id = ?")	){
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			 while(rs.next()) {
	     			result.append("<tr>");
	     			for(String eachRow: new String[]{"total_amount", "date", "transaction_id"}) {
	     				
	     				if(eachRow.equals("transaction_id")) {
	     					result.append("<td><a href='ReportServlet?action=viewPurchaseDetails&transactionId="+rs.getString(eachRow)+"'>"+rs.getString(eachRow)+"</a></td>");
	     				}else {
	     					result.append("<td>"+rs.getString(eachRow)+"</td>");
	     				}
	     			}
	     			result.append("</tr>");
		        	}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public String getTopCashier() {
		StringBuffer result = new StringBuffer();
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("SELECT  "
    					+ "	u.id As cashier_id, "
    					+ "    u.user_name AS cashier_name, "
    					+ "    COUNT(t.id) AS transaction_count "
    					+ "FROM  "
    					+ "    transactions t "
    					+ "JOIN  "
    					+ "    user u ON t.cashier_id = u.id "
    					+ "WHERE  "
    					+ "    t.cashier_id IS NOT NULL "
    					+ "GROUP BY  "
    					+ "    u.id, u.user_name "
    					+ "ORDER BY  "
    					+ "    transaction_count DESC;")	) {
			
			rs = ps.executeQuery();
			 while(rs.next()) {
	     			result.append("<tr>");
	     			for(String eachRow: new String[]{"cashier_id", "cashier_name", "transaction_count"}) {
	     				result.append("<td>"+rs.getString(eachRow)+"</td>");
	     			}
	     			result.append("</tr>");
		        	}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public String getUnsoldProducts() {
		StringBuffer result = new StringBuffer();
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("SELECT  "
    					+ "    p.id, "
    					+ "    p.name, "
    					+ "    p.stock_left, "
    					+ "    p.price "
    					+ "FROM  "
    					+ "    products p "
    					+ "LEFT JOIN  "
    					+ "    invoice_items ii ON p.id = ii.product_id "
    					+ "WHERE  "
    					+ "    ii.product_id IS NULL; ")	){
			
			rs = ps.executeQuery();
			 while(rs.next()) {
	     			result.append("<tr>");
	     			for(String eachRow: new String[]{"id", "name", "stock_left", "price"}) {
	     				result.append("<td>"+rs.getString(eachRow)+"</td>");
	     			}
	     			result.append("</tr>");
		        	}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public String getOutOfStockProducts() {
		StringBuffer result = new StringBuffer();
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("SELECT  "
    					+ "    id, "
    					+ "    name, "
    					+ "    price "
    					+ "FROM "
    					+ "    products "
    					+ "WHERE  "
    					+ "    stock_left = 0;")	){
			
			
			rs = ps.executeQuery();
			 while(rs.next()) {
	     			result.append("<tr>");
	     			for(String eachRow: new String[]{"id", "name", "price"}) {
	     				result.append("<td>"+rs.getString(eachRow)+"</td>");
	     			}
	     			result.append("</tr>");
		        	}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public String getPurchaseDetails(int transactionId) {
		StringBuffer result = new StringBuffer();
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("SELECT  "
        				+ "    ii.product_id, "
        				+ "    p.name as product_name, "
        				+ "    ii.quantity AS product_quantity, "
        				+ "    ii.price AS product_cost "
        				+ "FROM  "
        				+ "    transactions t "
        				+ "JOIN  "
        				+ "    invoices i ON t.id = i.transaction_id "
        				+ "JOIN  "
        				+ "    invoice_items ii ON i.id = ii.invoice_id "
        				+ "JOIN  "
        				+ "	products p ON ii.product_id = p.id "
        				+ "WHERE  "
        				+ "    t.id = ?;")	){
			
			ps.setInt(1, transactionId);
			rs = ps.executeQuery();
			 while(rs.next()) {
	     			result.append("<tr>");
	     			for(String eachRow: new String[]{"product_id", "product_name", "product_quantity", "product_cost"}) {
	     				result.append("<td>"+rs.getString(eachRow)+"</td>");
	     			}
	     			result.append("</tr>");
		        	}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
}
