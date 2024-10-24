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

@WebServlet("/loadUser")
public class LoadUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String customerPhoneNumber = request.getParameter("customerPhoneNumber");
		HttpSession session = request.getSession(false);
		UserService userService = new UserService();
		User user = userService.checkUser(customerPhoneNumber);
		session.setAttribute("customerPhoneNumber", customerPhoneNumber);
		if(user != null) {
			session.setAttribute("customer", user);
			session.setAttribute("customerExist", "Proceed with billing!");
			session.removeAttribute("userNotExist");
		}else {
			session.setAttribute("userNotExist", "Please Create user!");
			session.removeAttribute("customerExist");
			
		}
		request.getRequestDispatcher("cashier.jsp").forward(request, response);
	}

}
