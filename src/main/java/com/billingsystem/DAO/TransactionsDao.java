package com.billingsystem.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

import com.billingsystem.Model.Transactions;
import com.billingsystem.utility.DBConnectionPoolUtil;

public class TransactionsDao {
	public void saveTransactions(Transactions transaction) {
		
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("INSERT INTO Transactions(user_id, amount, transaction_date, cashier_id) VALUES (?, ?, ?,?)", Statement.RETURN_GENERATED_KEYS)	) {
			ps.setLong(1, transaction.getCustomer_id());
			ps.setDouble(2, transaction.getAmount());
			ps.setString(3, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(transaction.getTransaction_date()));
			ps.setLong(4, transaction.getCashier_id());
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				transaction.setTransaction_id(rs.getInt(1));
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public double getTotalTransaction() {
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("select sum(amount) as total_amount from transactions;")	) {
			
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getDouble("total_amount");
			}
			return 0;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
