package com.billingsystem.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.billingsystem.utility.DBConnectionUtil;

public class ReportDao {
	ResultSet rs = null;
	public ResultSet getTopSessingProducts() {
		
		try {
			PreparedStatement ps =  DBConnectionUtil.getInstance().getConnection().prepareStatement("SELECT p.id, p.name, SUM(ii.quantity) AS total_quantity_sold "
					+ "FROM products p "
					+ "JOIN invoice_items ii ON p.id = ii.product_id "
					+ "GROUP BY p.id, p.name "
					+ "ORDER BY total_quantity_sold DESC "
					+ "LIMIT 10;");
			
			 rs = ps.executeQuery();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getTopCustomers() {
		try {
			PreparedStatement ps =  DBConnectionUtil.getInstance().getConnection().prepareStatement("SELECT u.id, u.user_name, SUM(i.total_amount) AS total_spent " 
					+ "FROM user u "
					+ "JOIN invoices i ON u.id = i.user_id "
					+ "GROUP BY u.id, u.user_name "
					+ "ORDER BY total_spent DESC "
					+ "LIMIT 10;");
			
			rs = ps.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getInactiveCustomers(){
		try {
			PreparedStatement ps = DBConnectionUtil.getInstance().getConnection().prepareStatement("SELECT u.id, u.user_name " 
					+ "FROM user u "
					+ "LEFT JOIN invoices i ON u.id = i.user_id AND i.date > NOW() - INTERVAL 1 MONTH "
					+ "WHERE i.id IS NULL "
					+ "AND u.role = 'CUSTOMER';");
			rs = ps.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	public ResultSet getDailyRevenue(){
		try {
			PreparedStatement ps = DBConnectionUtil.getInstance().getConnection().prepareStatement("SELECT DATE(i.date) AS day, SUM(i.total_amount) AS total_revenue "
					+ "FROM invoices i "
					+ "WHERE i.date >= CURDATE() - INTERVAL 7 DAY "
					+ "GROUP BY DATE(i.date) "
					+ "ORDER BY day DESC;");
			rs = ps.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getSignedUpButNoPurchase() {
		try {
			PreparedStatement ps = DBConnectionUtil.getInstance().getConnection().prepareStatement("SELECT u.id, u.user_name "
					+ "FROM user u "
					+ "LEFT JOIN invoices i ON u.id = i.user_id "
					+ "WHERE i.id IS NULL "
					+ "AND u.role = 'CUSTOMER';");
			rs = ps.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getLowStockProducts() {
		try {
			PreparedStatement ps = DBConnectionUtil.getInstance().getConnection().prepareStatement("SELECT id, name, stock_left "
					+ "FROM products "
					+ "WHERE stock_left < usual_stock;");
			rs = ps.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getfrequentlyBoughtItem(int user_id) {
		try {
			PreparedStatement ps = DBConnectionUtil.getInstance().getConnection().prepareStatement("SELECT p.id, p.name, SUM(ii.quantity) AS total_quantity_bought "
					+ "FROM products p "
					+ "JOIN invoice_items ii ON p.id = ii.product_id "
					+ "JOIN invoices i ON ii.invoice_id = i.id "
					+ "WHERE i.user_id = ? "
					+ "GROUP BY p.id, p.name "
					+ "ORDER BY total_quantity_bought DESC;");
			ps.setInt(1, user_id);
			rs = ps.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getAllBillStatements(int userId) {
		try {
			PreparedStatement ps = DBConnectionUtil.getInstance().getConnection().prepareStatement("SELECT total_amount, date, transaction_id from invoices where user_id = ?");
			ps.setInt(1, userId);
			rs = ps.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getTopCashier() {
		try {
			PreparedStatement ps =  DBConnectionUtil.getInstance().getConnection().prepareStatement("SELECT  "
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
					+ "    transaction_count DESC;");
			
			rs = ps.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getUnsoldProducts() {
		try {
			PreparedStatement ps =  DBConnectionUtil.getInstance().getConnection().prepareStatement("SELECT  "
					+ "    p.id, "
					+ "    p.name, "
					+ "    p.stock_left, "
					+ "    p.price "
					+ "FROM  "
					+ "    products p "
					+ "LEFT JOIN  "
					+ "    invoice_items ii ON p.id = ii.product_id "
					+ "WHERE  "
					+ "    ii.product_id IS NULL; "
					);
			
			rs = ps.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getOutOfStockProducts() {
		try {
			PreparedStatement ps =  DBConnectionUtil.getInstance().getConnection().prepareStatement("SELECT  "
					+ "    id, "
					+ "    name, "
					+ "    price "
					+ "FROM "
					+ "    products "
					+ "WHERE  "
					+ "    stock_left = 0;"
					);
			
			rs = ps.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
}
