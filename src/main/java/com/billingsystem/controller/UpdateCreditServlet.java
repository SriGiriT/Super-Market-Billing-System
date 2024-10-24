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

@WebServlet("/updateCredit")
public class UpdateCreditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        User adminUser = (User) session.getAttribute("user");
        System.out.println(adminUser);
        if (adminUser != null && "ADMIN".equals(adminUser.getRole().toString())) {
            String mobileNumber = request.getParameter("mobileNumber");
            Double credit = Double.parseDouble(request.getParameter("credit"));
            
            UserService userService = new UserService();
            boolean isRoleAssigned = userService.assignCurrentCredit(mobileNumber, credit);
            session.setAttribute("userAndCredit", userService.getUsersCreditDetails());
            if (isRoleAssigned) {
                request.setAttribute("message", "Credit updated successfully!");
            } else {
            	System.out.println("Fail to Assign");
                request.setAttribute("message", "Credit updation failed.");
            }
            request.getRequestDispatcher("creditAssignment.jsp").forward(request, response);
        } else {
        	System.out.println("Unauthorized!");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to assign credit.");
        }
	}

}
