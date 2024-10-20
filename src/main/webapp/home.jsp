<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
    <link rel="stylesheet" href="styles.css"> 
</head>
<body>
    <h2>Welcome, ${user.userName}!</h2>
    

    <p>You are logged in as <strong>${user.role}</strong>.</p>
	<p>Information:<br/> Name: ${user.userName} <br/> Phone Number: ${user.phoneNumber} <br/>  Email: ${user.email} <br/>  Points: ${user.points} <br/>  Credits: ${user.current_credit} <br/> </p>
	
	<c:if test="${user.role == 'ADMIN'}">
        <p><a href="roleAssignment.jsp">Assign Role</a></p>
    </c:if>
    <c:if test="${user.role == 'ADMIN' || user.role == 'CUSTOMER'}">
    	<p><a href="reports.jsp">View Reports</a></p>
    </c:if>
    <p><a href="products">View Products</a></p>
    <p><a href="change_password.jsp">Change Password</a></p>
    <p><a href="login.jsp">Logout</a></p>
</body>
</html>
