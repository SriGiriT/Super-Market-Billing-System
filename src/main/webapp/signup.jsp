<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h2>Sign Up for Supermarket Billing System</h2>
    
    <form action="signup" method="post">
     	<label for="userName">User Name:</label>
        <input type="text" id="userName" name="userName" required><br><br>
        
        <label for="phoneNumber">Mobile Number:</label>
        <input type="text" id="phoneNumber" name="phoneNumber" required><br><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" required><br><br>
        
        <input type="submit" value="Sign Up">
    </form>

    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>

    <br>
    <p>Already have an account? <a href="login.jsp">Login here</a></p>
</body>
</html>
