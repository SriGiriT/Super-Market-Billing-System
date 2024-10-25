package com.billingsystem.service;


import java.util.Map;

import com.billingsystem.DAO.UserDao;
import com.billingsystem.Model.User;
import com.billingsystem.utility.PasswordUtil;

public class UserService {
    private UserDao userDao = new UserDao();

    public User login(String phoneNumber, String password) {
        User user = userDao.findUserByPhoneNumber(phoneNumber);
        if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }
    
    public User checkUser(String phoneNumber) {
    	User user = userDao.findUserByPhoneNumber(phoneNumber);
    	if(user != null) {
    		return user;
    	}
    	return null;
    }
    public boolean makeTransaction(User user, double totalAmount) {
    	user.setCurrent_credit(user.getCurrent_credit()-totalAmount);
    	user.setPoints(user.getPoints()+totalAmount/100);
    	return userDao.makeTransaction(user, totalAmount);
    }
    public void updatePassword(User user) {
    	userDao.updatePassword(user);
    }

    public boolean createUser(User user) {
        return userDao.save(user);
    }
    public boolean assignRole(String phoneNumber, String role) {
    	return userDao.assignRole(phoneNumber, role);
    }
    public boolean assignCurrentCredit(String phoneNumber, double current_credit) {
    	return userDao.assignCurrentCredit(phoneNumber, current_credit);
    }
    public boolean isEmailExist(String email) {
    	return userDao.isEmailExist(email);
    }

    public Map<String, String> getUsersRoleDetails() {
		return userDao.getUsersRoleDetails();
	}

	public Map<String, Double> getUsersCreditDetails() {
		return userDao.getUsersCreditDetails();
	}
	public static void main(String[] args) {
		UserService userDao = new UserService();
		System.out.println(" kljflkjsf ");
		System.out.println(userDao.login("9344953230", "9344953230"));
	}
}
