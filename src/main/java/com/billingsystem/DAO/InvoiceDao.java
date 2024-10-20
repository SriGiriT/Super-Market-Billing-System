package com.billingsystem.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.billingsystem.Model.CartItem;
import com.billingsystem.Model.Invoice;
import com.billingsystem.utility.DBConnectionUtil;

public class InvoiceDao {

    public void saveInvoice(Invoice invoice) {

        try {
            String sql = "INSERT INTO invoices (user_id, total_amount, date, transaction_id) VALUES (?, ?, ?, ?)";
            PreparedStatement ps =  DBConnectionUtil.getInstance().getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, invoice.getCustomer().getId());
            ps.setDouble(2, invoice.getTotalAmount());
            ps.setDate(3, new java.sql.Date(invoice.getDate().getTime()));
            ps.setInt(4, invoice.getTransaction_id());

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
        String sql = "INSERT INTO invoice_items (invoice_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        PreparedStatement ps =  DBConnectionUtil.getInstance().getConnection().prepareStatement(sql);
        ps.setInt(1, invoice.getId()); 
        ps.setLong(2, item.getProduct().getId());
        ps.setInt(3, item.getQuantity());
        ps.setDouble(4, item.getProduct().getPrice());

        ps.executeUpdate();
    }
}
