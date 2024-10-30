package com.billingsystem.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.billingsystem.DAO.UserDao;
import com.billingsystem.service.VerificationService;


@WebServlet("/verify")
public class VerificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getParameter("token");
		Long user_id = Long.parseLong(request.getParameter("user_id"));
		UserDao userDao = new UserDao();
		VerificationService verificationService = new VerificationService();
        boolean isVerified = verificationService.isValidEmailUser(user_id, token);
        
        if (isVerified) {
        	userDao.verifyUser(user_id);
            response.sendRedirect("verified.jsp");
        } else {
            response.sendRedirect("verification_failed.jsp");
        }
	}


}
