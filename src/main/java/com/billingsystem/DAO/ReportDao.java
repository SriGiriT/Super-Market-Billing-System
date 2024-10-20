package com.billingsystem.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.billingsystem.utility.DBConnectionUtil;

public class ReportDao {
	public ResultSet getTopSessingProducts() {
		ResultSet rs = null;
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
		ResultSet rs = null;
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
		ResultSet rs = null;
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
		ResultSet rs = null;
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
		ResultSet rs = null;
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
		ResultSet rs = null;
		try {
			PreparedStatement ps = DBConnectionUtil.getInstance().getConnection().prepareStatement("SELECT id, name, stockLeft "
					+ "FROM products "
					+ "WHERE stockLeft < usualStock;");
			rs = ps.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public ResultSet getfrequentlyBoughtItem(int user_id) {
		ResultSet rs = null;
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
}
