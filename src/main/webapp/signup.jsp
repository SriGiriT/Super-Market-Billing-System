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
    
    <form action="signup" method="post" onsubmit="return checkLogin(this);">
     	<label for="userName">User Name:</label>
        <input type="text" id="userName" name="userName" required><br><br>
        
        <label for="phoneNumber">Mobile Number:</label>
        <input type="text" id="phoneNumber" name="phoneNumber" required><br><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <label for="confirmPassword">Confirm Password:</label>
        <input type="password" id="confirmPassword" name="confirmPassword" required><br><br>
        
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" required><br><br>
        
        <input type="submit" value="Sign Up">
    </form>

    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>

    <br>
    <p>Already have an account? <a href="login.jsp">Login here</a></p>
    <script type="text/javascript">
    	function checkLogin(form){
    		if(form.userName.value == ""){
    			alert("Username can't be empty!");
    			form.userName.focus();
    			return false;
    		}
    		exp = /[0-9]{10}/;
    		if(!exp.test(form.phoneNumber.value)){
    			alert("Invalid phone number!");
    			form.phoneNumber.focus();
    			return false;
    		}
    		if(form.password.value == ""){
    			alert("Please provide password!");
    			form.password.focus();
    			return false;
    		}
    		if(form.confirmPassword.value == ""){
    			alert("Re-enter password for confirmation!");
    			form.confirmPassword.focus();
    			return false;
    		}
    		if(form.password.value.length < 8){
    			alert("Password must at least contains 8 characters");
    			form.password.focus();
    			return false;
    		}
    		exp = /[0-9]/;  
    		if(!exp.test(form.password.value)){
    			alert("Password must contains at least one number(0-9)");
    			form.password.focus();
    			return false;
    		}
    		exp =  /[a-z]/;  
    		if(!exp.test(form.password.value)){
    			alert("Password must contains at least one small letter(a-z)");
    			form.password.focus();
    			return false;
    		}
    		exp = /[A-Z]/;
    		if(!exp.test(form.password.value)){
    			alert("Password must contains at least one capital letter(A-Z)");
    			form.password.focus();
    			return false;
    		}
    		if(form.password.value !== form.confirmPassword.value){
    			alert("Password and confirm password missmatches!");
    			form.confirmPassword.focus();
    			return false;
    		}
    		exp = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!exp.test(form.email.value)) {
                alert("Please enter a valid email address.");
                form.email.focus();
                return false;
            }
    		return true;
    	}
    </script>
</body>
</html>
