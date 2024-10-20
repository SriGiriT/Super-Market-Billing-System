package com.billingsystem.service;


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
}
