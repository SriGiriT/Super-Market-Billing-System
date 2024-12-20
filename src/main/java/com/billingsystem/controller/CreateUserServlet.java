package com.billingsystem.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.billingsystem.Model.User;
import com.billingsystem.service.UserService;
import com.billingsystem.utility.PasswordUtil;

@WebServlet("/createUser")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserService userService = new UserService();
		HttpSession session = request.getSession(false);
		String customerPhoneNumber = request.getParameter("customerPhoneNumber").toString();
		String customerEmail = request.getParameter("customerEmail").toString();
		String customerUserName = request.getParameter("customerUserName").toString();
		User user = new User();
		user.setRole(User.Role.CUSTOMER);
		user.setPhoneNumber(customerPhoneNumber);
		user.setPassword(PasswordUtil.hashPassword(customerPhoneNumber));
		user.setEmail(customerEmail);
		user.setUserName(customerUserName);
		if(userService.isEmailExist(customerEmail)) {
			session.setAttribute("customerName", customerUserName);
        	session.setAttribute("errorInCreateUser", "Provided Email already linked with existing account\nPlease try with different Email");
        }else {
        	userService.createUser(user);
        	session.setAttribute("customerPhoneNumber", user.getPhoneNumber());
        	session.removeAttribute("errorInCreateUser");
    		session.removeAttribute("userNotExist");
    		session.setAttribute("customerExist", "Customer Created You can Proceed with Billing!");
    		
        }
		request.getRequestDispatcher("products").forward(request, response);
		
	}

}
