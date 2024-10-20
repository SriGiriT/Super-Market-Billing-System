package com.billingsystem.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.billingsystem.Model.User;
import com.billingsystem.service.UserService;
import com.billingsystem.utility.PasswordUtil;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String userName = request.getParameter("userName");
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");
        String hashedPassword = PasswordUtil.hashPassword(password); 
        String email = request.getParameter("email");

        UserService userService = new UserService();
        
        User existingUser = userService.checkUser(phoneNumber);
        if (existingUser != null) {
            request.setAttribute("errorMessage", "Mobile number already registered.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return; 
        }

        User user = new User();
        user.setUserName(userName);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(hashedPassword); 
        user.setRole(User.Role.CUSTOMER);
        user.setEmail(email);

        boolean isRegistered = userService.createUser(user);

        if (isRegistered) {
            response.sendRedirect("login.jsp"); 
        } else {
            request.setAttribute("errorMessage", "Signup failed due to internal error.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }
}
