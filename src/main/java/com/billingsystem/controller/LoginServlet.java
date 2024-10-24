package com.billingsystem.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.billingsystem.Model.User;
import com.billingsystem.service.UserService;
import com.billingsystem.utility.JWTUtil;
import com.billingsystem.utility.LoggerUtil;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");

        UserService userService = new UserService();
        User user = userService.login(phoneNumber, password);
        HttpSession session = request.getSession();
        if (user != null) {
            session.setAttribute("user", user);
            String token = JWTUtil.generateToken(user.getPhoneNumber(), user.getRole().toString());
            Cookie cookie = new Cookie("auth_token", token);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(300*60);
            response.addCookie(cookie);
            response.sendRedirect("home.jsp");
        } else {
            request.setAttribute("errorMessage", "Invalid credentials!");
            session.setAttribute("phoneNumber", phoneNumber);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}

