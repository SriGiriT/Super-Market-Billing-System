package com.billingsystem.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.billingsystem.DAO.UserDao;
import com.billingsystem.Model.User;
import com.billingsystem.service.UserService;
import com.billingsystem.utility.LoggerUtil;
import com.billingsystem.utility.PasswordUtil;

@WebServlet("/changePassword")
public class ChangePasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User loggedInUser = (User) (session.getAttribute("user") == null ? new UserDao().findUserByPhoneNumber(request.getParameter("phoneNumber")) : session.getAttribute("user") ); 
        String existingPassword = request.getParameter("existingPassword");
        LoggerUtil.getInstance().getLogger().debug("in change password".toString() + loggedInUser.toString() + existingPassword);
        if (loggedInUser != null) {
        	LoggerUtil.getInstance().getLogger().debug(loggedInUser.toString());
        	if(userService.login(loggedInUser.getPhoneNumber(), existingPassword) != null){
        		String newPassword = request.getParameter("password");
                String hashedPassword = PasswordUtil.hashPassword(newPassword);
                loggedInUser.setPassword(hashedPassword);
                userService.updatePassword(loggedInUser); 
                request.setAttribute("successMessage", "Password Updated Successfully!");
        	}else {
        		request.setAttribute("errorMessage", "Existing Password is wrong, Try again!");
        	}
        } 
        request.getRequestDispatcher("change_password.jsp").forward(request, response);
    }
}
