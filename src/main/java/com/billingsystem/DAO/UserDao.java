package com.billingsystem.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.billingsystem.Model.User;
import com.billingsystem.Model.User.Role;
import com.billingsystem.utility.DBConnectionPoolUtil;
import com.billingsystem.utility.LoggerUtil;

public class UserDao {
    public User findUserByPhoneNumber(String phoneNumber) {
        try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("SELECT * FROM user WHERE phone_number = ?")	){
            ps.setString(1, phoneNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setEmail(rs.getString("email"));
                user.setPoints(rs.getDouble("points"));
                user.setCurrent_credit(rs.getDouble("current_credit"));
                return user;
            }
        } catch (SQLException e) {
        	LoggerUtil.getInstance().logException("error in findUserByPhoneNumber", e);
            e.printStackTrace();
        }
        return null;
    }
    
    

    public boolean save(User user) {
        String sql = "INSERT INTO user (user_name, password, phone_number, email, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement(sql)	) {
        	ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getRole().toString());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    public void updatePassword(User user) {
        String sql = "UPDATE user SET password = ? WHERE phone_number = ?";

        try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement(sql)	)  {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getPhoneNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public boolean assignRole(String phoneNumber, String role) {
    	String sql = "UPDATE user SET role = ? WHERE phone_number = ?";
    	
        try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement(sql)	)  {
            ps.setString(1, role);
            ps.setString(2, phoneNumber);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    
    public boolean assignCurrentCredit(String phoneNumber, double current_credit) {
    	String sql = "UPDATE user SET current_credit = ? WHERE phone_number = ?";
    	try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement(sql)	) {
    		ps.setDouble(1, current_credit);
    		ps.setString(2, phoneNumber);
    		ps.executeUpdate();
    		return true;
    	}catch(Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }



	public Map<String, String> getUsersRoleDetails() {
		String sql = "Select role, phone_number, user_name from user";
		Map<String, String> userAndRole = new HashMap<>();
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement(sql)	) {
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				userAndRole.put(rs.getString("phone_number")+"-"+rs.getString("user_name"),rs.getString("role"));
	            
			}
    		return userAndRole;
    	}catch(Exception e) {
    		e.printStackTrace();
    		return userAndRole;
		}
	}



	public Map<String, Double> getUsersCreditDetails() {
		String sql = "Select phone_number, current_credit, user_name from user";
		Map<String, Double> userAndRole = new HashMap<>();
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement(sql)	) {
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				userAndRole.put(rs.getString("phone_number")+"-"+rs.getString("user_name"),rs.getDouble("current_credit"));
	            
			}
    		return userAndRole;
    	}catch(Exception e) {
    		e.printStackTrace();
    		return userAndRole;
		}
	}



	public boolean isEmailExist(String email) {
		String sql = "SELECT * FROM user WHERE email = ?";
        try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement(sql)	) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               return true;
            }else {
            	return false;
            }
        } catch (SQLException e) {
        	LoggerUtil.getInstance().logException("error in findUserByEmail", e);
            e.printStackTrace();
            return true;
        }
		
	}



	public boolean makeTransaction(User user, double totalAmount) {
		String sql = "UPDATE user SET current_credit = ?, points = ? WHERE phone_number = ?";
    	try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement(sql)	) {
    		ps.setDouble(1, user.getCurrent_credit());
    		ps.setDouble(2, user.getPoints());
    		ps.setString(3, user.getPhoneNumber());
    		ps.executeUpdate();
    		return true;
    	}catch(Exception e) {
    		e.printStackTrace();
    		return false;
    	}
	}



	public boolean addPointsToCashier(long cashierId) {
		String sql = "UPDATE user SET points = points + 1 WHERE id = ?";
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement(sql)	) {
    		ps.setLong(1, cashierId);
    		ps.executeUpdate();
    		return true;
    	}catch(Exception e) {
    		e.printStackTrace();
    		return false;
    	}
	}


}
