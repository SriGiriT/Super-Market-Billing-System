package com.billingsystem.controller;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.billingsystem.Model.User;
import com.billingsystem.service.UserService;
import com.billingsystem.utility.EmailUtility;
import com.billingsystem.utility.PasswordUtil;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String userName = request.getParameter("userName");
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        HttpSession session = request.getSession(false);
        session.setAttribute("userName", userName);
        session.setAttribute("phoneNumber", phoneNumber);
        session.setAttribute("password", password);
        session.setAttribute("email", email);
        if(!isValidPassword(password)) {
        	request.setAttribute("errorMessage", "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character.");
        	request.getRequestDispatcher("signup.jsp").forward(request, response);
        	return;
        }
        String hashedPassword = PasswordUtil.hashPassword(password); 
        

        UserService userService = new UserService();
        
        
        Pattern pattern = Pattern.compile("\\d{10}");
        Matcher matcher = pattern.matcher(phoneNumber);
        if(!matcher.matches()) {
        	request.setAttribute("errorMessage", "Please Enter a valid Mobile number");
        	request.getRequestDispatcher("signup.jsp").forward(request, response);
        	return;
        }
        pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        matcher = pattern.matcher(email);
        if(!matcher.matches()) {
        	request.setAttribute("errorMessage", "Please Enter a valid Email Address");
        	request.getRequestDispatcher("signup.jsp").forward(request, response);
        	return;
        }
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
        if(userService.isEmailExist(email)) {
        	request.setAttribute("errorMessage", "Provided Email already linked with existing account\nPlease try with different Email");
        }else {
        	 boolean isRegistered = userService.createUser(user);
             if (isRegistered) {
             	session.setAttribute("successMessage", "Signed up Successfully!!!");
             	EmailUtility.sendVerificationEmail(user.getEmail(), UUID.randomUUID().toString(), user.getId());
                 response.sendRedirect("login.jsp"); 
                 return;
             } else {
                 request.setAttribute("errorMessage", "Signup failed due to internal error.");
             }
        }
        request.getRequestDispatcher("signup.jsp").forward(request, response);
       
    }
	
	
    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasNumber = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUppercase = true;
            else if (Character.isLowerCase(c)) hasLowercase = true;
            else if (Character.isDigit(c)) hasNumber = true;
        }

        return hasUppercase && hasLowercase && hasNumber;
    }
	
}
