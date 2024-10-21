<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="styles.css"> 
</head>
<body>
    <h2>Login to Supermarket Billing System</h2>
    
    <form action="login" method="post" onsubmit="return checkLogin(this);">
        <label for="phoneNumber">Mobile Number:</label>
        <input type="text" id="phoneNumber" name="phoneNumber" required><br><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <input type="submit" value="Login">
    </form>

    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>

    <br>
    <p>Don't have an account? <a href="signup.jsp">Sign Up</a></p>
    <script type="text/javascript">
    	function checkLogin(form){
    		exp = /[0-9]{10}/;
    		if(!exp.test(form.phoneNumber.value)){
    			alert("Invalid phone number!");
    			form.phoneNumber.focus();
    			return false;
    		}
    		return true;
    	}
    </script>
</body>
</html>
