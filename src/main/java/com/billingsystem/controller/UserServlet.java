package com.billingsystem.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.billingsystem.DAO.UserDao;
import com.billingsystem.Model.User;
import com.billingsystem.service.TransactionsService;
import com.billingsystem.service.UserService;
import com.billingsystem.utility.JWTUtil;
import com.billingsystem.utility.LoggerUtil;
import com.billingsystem.utility.PasswordUtil;



public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if(path == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL");
			return;
		}
		switch(path) {
			case "/login":
				handleLogin(request, response);
				break;
			case "/logout":
				handleLogout(request, response);
				break;
			case "/load":
				handleLoadUser(request, response);
				break;
			case "/changePassword":
				handleChangePassword(request, response);
				break;
			case "/create":
				handleCreateUser(request, response);
				break;
			case "/load/role":
				handleLoadField(request, response, "role");
				break;
			case "/load/credit":
				handleLoadField(request, response, "credit");
				break;
			case "assign/role":
				handleAssignField(request, response, "role");
				break;
			case "assign/credit":
				handleAssignField(request, response, "credit");
				break;
			
			
		}
	}
	
	private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String phoneNumber = request.getParameter("phoneNumber");
	        String password = request.getParameter("password");
	        
	        User user = userService.login(phoneNumber, password);
	        TransactionsService transactionService = new TransactionsService();
	        HttpSession session = request.getSession();
	        if (user != null) {
	            session.setAttribute("user", user);
	            if(user.getRole().toString().equals("ADMIN")) {
	            	session.setAttribute("totalEarnings", transactionService.getTotalTransaction());
	            }
	            String token = JWTUtil.generateToken(user.getPhoneNumber(), user.getRole().toString(), request.getRemoteAddr());
	            Cookie cookie = new Cookie("auth_token", token);
	            cookie.setHttpOnly(true);
	            cookie.setMaxAge(300*60);
	            response.addCookie(cookie);
	            if(PasswordUtil.checkPassword(phoneNumber, PasswordUtil.hashPassword(password))) {
	            	request.setAttribute("NeedToChange", "You have to change your password if you are login for first time!");
	            	request.getRequestDispatcher("/change_password.jsp").forward(request, response);
	            	return;
	            }
	            response.sendRedirect(request.getContextPath() + "/home.jsp");
	        } else {
	            request.setAttribute("errorMessage", "Invalid credentials!");
	            session.setAttribute("phoneNumber", phoneNumber);
	            request.getRequestDispatcher("/login.jsp").forward(request, response);
	        }
	}
	
	private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		session.invalidate();
		Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("auth_token".equals(cookie.getName())) {
                   cookie.setMaxAge(0);
                   response.addCookie(cookie);
                }
            }
        }
        
		response.sendRedirect("login.jsp");
	}
	
	private void handleLoadUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String customerPhoneNumber = request.getParameter("customerPhoneNumber");
		HttpSession session = request.getSession(false);
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
		request.getRequestDispatcher("products").forward(request, response);
	}
	private void handleChangePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	public void handleCreateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String customerPhoneNumber = request.getParameter("customerPhoneNumber").toString();
		String customerEmail = request.getParameter("customerEmail").toString();
		String customerUserName = request.getParameter("customerUserName").toString();
		User user = new User();
		user.setRole(User.Role.CUSTOMER);
		user.setPhoneNumber(customerPhoneNumber);
		user.setPassword(PasswordUtil.hashPassword(customerPhoneNumber));
		user.setEmail(customerEmail);
		user.setUserName(customerUserName);
		if(userService.isEmailExist(customerEmail)) {
			session.setAttribute("customerName", customerUserName);
        	session.setAttribute("errorInCreateUser", "Provided Email already linked with existing account\nPlease try with different Email");
        }else {
        	userService.createUser(user);
        	session.setAttribute("customerPhoneNumber", user.getPhoneNumber());
        	session.removeAttribute("errorInCreateUser");
    		session.removeAttribute("userNotExist");
    		session.setAttribute("customerExist", "Customer Created You can Proceed with Billing!");
    		
        }
		request.getRequestDispatcher("products").forward(request, response);
	}
	
	public void handleLoadField(HttpServletRequest request, HttpServletResponse response, String field) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        User adminUser = (User) session.getAttribute("user");
        UserService userService = new UserService();
        
        if (adminUser != null && "ADMIN".equals(adminUser.getRole().toString())) {
        	if(field.equalsIgnoreCase("role")) {
        		session.setAttribute("userAndRole", userService.getUsersRoleDetails());
                request.getRequestDispatcher("roleAssignment.jsp").forward(request, response);
        	}else if(field.equalsIgnoreCase("credit")) {
        		session.setAttribute("userAndCredit", userService.getUsersCreditDetails());
                request.getRequestDispatcher("creditAssignment.jsp").forward(request, response);
        	}else {
        		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Field");
        	}
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to assign roles.");
        }
	}
	
	public void handleAssignField(HttpServletRequest request, HttpServletResponse response, String field) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        User adminUser = (User) session.getAttribute("user");
        System.out.println(adminUser);
        if (adminUser != null && "ADMIN".equals(adminUser.getRole().toString())) {
            String mobileNumberAndName = request.getParameter("mobileNumber");
            String mobileNumber = mobileNumberAndName.split("-")[0];
            if(field.equalsIgnoreCase("role")) {
            	String role = request.getParameter("role");
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
            }else if(field.equalsIgnoreCase("credit")) {
            	Double credit = Double.parseDouble(request.getParameter("credit"));
            	boolean isRoleAssigned = userService.assignCurrentCredit(mobileNumber, credit);
                session.setAttribute("userAndCredit", userService.getUsersCreditDetails());
                if (isRoleAssigned) {
                    request.setAttribute("message", "Credit updated successfully!");
                } else {
                	System.out.println("Fail to Assign");
                    request.setAttribute("message", "Credit updation failed.");
                }
                request.getRequestDispatcher("creditAssignment.jsp").forward(request, response);
            }else {
            	response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL");
            }
        } else {
        	System.out.println("Unauthorized!");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to assign roles.");
        }
	}
}
