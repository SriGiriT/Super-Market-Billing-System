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

@WebServlet("/loadUsersAndRoles")
public class LoadUserAndAssignRoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        User adminUser = (User) session.getAttribute("user");
        UserService userService = new UserService();
        
        if (adminUser != null && "ADMIN".equals(adminUser.getRole().toString())) {
            session.setAttribute("userAndRole", userService.getUsersRoleDetails());
            request.getRequestDispatcher("roleAssignment.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to assign roles.");
        }
	}


}
