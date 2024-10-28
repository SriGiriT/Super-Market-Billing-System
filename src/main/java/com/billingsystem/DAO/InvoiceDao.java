package com.billingsystem.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

import com.billingsystem.Model.CartItem;
import com.billingsystem.Model.Invoice;
import com.billingsystem.utility.DBConnectionPoolUtil;
import com.billingsystem.utility.LoggerUtil;

public class InvoiceDao {

    public void saveInvoice(Invoice invoice) {

        try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("INSERT INTO invoices (user_id, total_amount, date, transaction_id) VALUES (?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);){
            ps.setLong(1, invoice.getCustomer().getId());
            ps.setDouble(2, invoice.getTotalAmount());
            ps.setString(3, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(invoice.getDate()));
            ps.setInt(4, invoice.getTransaction_id());
            LoggerUtil.getInstance().getLogger().debug("transaction ID "+invoice.getTransaction_id());
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
            	invoice.setId(rs.getInt(1));
            }

            for (CartItem item : invoice.getCart().getItems()) {
                saveCartItem(invoice, item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveCartItem(Invoice invoice, CartItem item) throws SQLException {
    	try (Connection connection = DBConnectionPoolUtil.getConnection();
    			PreparedStatement ps = connection.prepareStatement("INSERT INTO invoice_items (invoice_id, product_id, quantity, price) VALUES (?, ?, ?, ?)");   ) {
    		ps.setInt(1, invoice.getId()); 
            ps.setLong(2, item.getProduct().getId());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getProduct().getPrice());

            ps.executeUpdate();
    	}catch(SQLException e) {
    		LoggerUtil.getInstance().getLogger().debug(e.toString());
    	}
        
    }
}
