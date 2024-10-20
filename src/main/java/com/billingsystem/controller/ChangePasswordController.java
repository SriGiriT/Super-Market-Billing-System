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

@WebServlet("/changePassword")
public class ChangePasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private UserService UserService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User loggedInUser = (User) session.getAttribute("user"); 

        if (loggedInUser != null) {
            String newPassword = request.getParameter("newPassword");

            String hashedPassword = PasswordUtil.hashPassword(newPassword);
            loggedInUser.setPassword(hashedPassword);
            UserService.updatePassword(loggedInUser); 
            response.sendRedirect("home.jsp");
        } else {
            response.sendRedirect("login.jsp"); 
        }
    }
}
