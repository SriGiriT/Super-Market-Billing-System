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
@WebServlet("/assignRole")
public class RoleAssignmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User adminUser = (User) session.getAttribute("user");
        System.out.println(adminUser);
        if (adminUser != null && "ADMIN".equals(adminUser.getRole().toString())) {
            String mobileNumberAndName = request.getParameter("mobileNumber");
            String mobileNumber = mobileNumberAndName.split("-")[0];
            String role = request.getParameter("role");
            
            UserService userService = new UserService();
            boolean isRoleAssigned = userService.assignRole(mobileNumber, role);
            session.setAttribute("userAndRole", userService.getUsersRoleDetails());
            if (isRoleAssigned) {
            	System.out.println("Role Assigned!");
                request.setAttribute("message", "Role assigned successfully!");
            } else {
            	System.out.println("Fail to Assign");
                request.setAttribute("message", "Role assignment failed.");
            }
            request.getRequestDispatcher("roleAssignment.jsp").forward(request, response);
        } else {
        	System.out.println("Unauthorized!");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to assign roles.");
        }
    }
}
