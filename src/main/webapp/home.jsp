<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
     <link rel="stylesheet" href="style_home.css"> 
</head>
<body style="padding-top:0px;">
<div class="content-container" 	style="max-width: 1200px;padding: 30px;">
    <h2>Welcome, ${user.userName}!</h2>
    
	<%
    
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
    response.setHeader("Pragma", "no-cache"); 
    response.setHeader("Expires", "0"); 
    if(session.getAttribute("user") == null){
    	response.sendRedirect("login.jsp");
    }
    session.removeAttribute("successMessage");
	
    %>
    
   
    <p>You are logged in as <strong>${user.role}</strong>.</p>
	<p>Information:<br/> Name: ${user.userName} <br/> Phone Number: ${user.phoneNumber} <br/>  Email: ${user.email} <br/>
	<c:if test="${user.role == 'CUSTOMER' || user.role == 'INVENTORY_MANAGER'}">
	    Credits: ${user.current_credit} <br/> 
	</c:if>
	<c:if test="${user.role != 'ADMIN'}">
		Points: <fmt:formatNumber value="${user.points}" pattern="#,##0.00" /><br/>
	</c:if>
	<c:if test="${user.role == 'ADMIN'}">
		Total Earned: ${totalEarnings}  <br/>
	</c:if>

    </p>
    <a href="change_password.jsp">Change password</a>
    </div>
</body>
</html>
