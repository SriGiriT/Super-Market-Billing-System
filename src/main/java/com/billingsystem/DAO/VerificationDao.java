package com.billingsystem.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.billingsystem.utility.DBConnectionPoolUtil;

public class VerificationDao {
	public void saveVerificationCode(long user_id, String verificationCode) {

        try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("insert into verification_token values(?, ?);");){
            ps.setLong(1, user_id);
            ps.setString(2, verificationCode);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	public boolean isValidEmailUser(long user_id, String verificationCode) {
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("select * from verification_token where user_id = ? and verfication_code = ?;");){
            ps.setLong(1, user_id);
            ps.setString(2, verificationCode);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
}
