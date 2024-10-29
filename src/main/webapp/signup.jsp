<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign Up</title>
    <link rel="stylesheet" href="styles.css">
     <style>
     	/* Reset some default styling */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

/* Body styling */
body {
    font-family: Arial, sans-serif;
    background-color:#f4f4f9;
   	background-image:url('https://images.pexels.com/photos/264636/pexels-photo-264636.jpeg');
   	background-size: cover;     backdrop-filter: blur(5px);   
    background-position: center;   
    background-repeat: no-repeat;  
    color: #ffffff;
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
}

/* Common container styling */
.logsignform, .content-container {
    width: 400px;
    background-color: #ffffff;
    padding: 20px;
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
    border-radius: 8px;
    text-align: center;
    margin-top: 20px;
}

/* Header */
h2 {
    font-size: 24px;
    color: #4CAF50;
    margin-bottom: 20px;
}

/* Labels */
label {
    display: block;
    font-size: 14px;
    margin: 10px 0 5px;
    text-align: left;
}

/* Inputs */
input[type="text"], input[type="password"], input[type="submit"] {
    width: calc(100% - 20px);
    padding: 10px;
    margin: 8px 0;
    border-radius: 4px;
    border: 1px solid #ddd;
    font-size: 16px;
}

input[type="submit"] {
    background-color: #4CAF50;
    color: white;
    border: none;
    cursor: pointer;
    transition: background-color 0.3s;
}

input[type="submit"]:hover {
    background-color: #45a049;
}

/* Success and Error Messages */
#error, #successMessage {
    font-size: 14px;
    margin: 10px 0;
    padding: 8px;
    border-radius: 4px;
    display: flex;
    justify-content: center;
    text-align: center;
}

#error {
    color: #d9534f;
    background-color: #f9e6e6;
}

#successMessage {
    color: #28a745;
    background-color: #e6f9e6;
}

/* Links */
a {
    color: #4CAF50;
    text-decoration: none;
}

a:hover {
    text-decoration: underline;
}

/* Home page specific */
.content-container h2 {
    color: #333;
}

.content-container p {
    margin: 10px 0;
}

#logoutForm {
    margin-top: 20px;
}

#logoutForm a {
    color: #d9534f;
}

a[target="_blank"] {
    color: #333;
}

/* Responsive */
@media (max-width: 480px) {
    .logsignform, .content-container {
        width: 100%;
        margin: 10px;
        padding: 15px;
    }

    h2 {
        font-size: 20px;
    }

    input[type="text"], input[type="password"], input[type="submit"] {
        font-size: 14px;
    }
}
     	
     </style>
</head>
<body>
<div  class="logsignform" style="background: rgba( 0, 0, 0, 0.8 );
box-shadow: 0 8px 32px 0 rgba( 31, 38, 135, 0.37 );
backdrop-filter: blur( 20px );
-webkit-backdrop-filter: blur( 20px );
border-radius: 10px;
border: 1px solid rgba( 255, 255, 255, 0.18 );">
    <h2>Sign Up</h2>
    
    <form action="signup" method="post" onsubmit="return checkLogin(this);">
     	<label for="userName">User Name:</label>
        <input type="text" id="userName" name="userName" value="${userName}" required><br><br>
        
        <label for="phoneNumber">Mobile Number:</label>
        <input type="text" id="phoneNumber" name="phoneNumber"  value="${phoneNumber}" required><br><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" value="${password}" required><br><br>
        
        <label for="confirmPassword">Confirm Password:</label>
        <input type="password" id="confirmPassword" name="confirmPassword" value="${password}"  required><br><br>
        
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" value="${email}"  required><br><br>
        
        <input type="submit" value="Sign Up">
    </form>

    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>

    <br>
    <p>Already have an account? <a href="login.jsp">Login here</a></p>
    </div>
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
