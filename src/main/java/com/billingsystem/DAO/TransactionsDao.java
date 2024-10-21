package com.billingsystem.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.billingsystem.Model.Transactions;
import com.billingsystem.utility.DBConnectionUtil;

public class TransactionsDao {
	public void saveTransactions(Transactions transaction) {
		
		try {
			String sql = "INSERT INTO Transactions(user_id, amount, transaction_date) VALUES (?, ?, ?)";
			PreparedStatement ps = DBConnectionUtil.getInstance().getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, transaction.getCustomer_id());
			ps.setDouble(2, transaction.getAmount());
			ps.setDate(3, new java.sql.Date(transaction.getTransaction_date().getTime()));
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				transaction.setTransaction_id(rs.getInt(1));
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
