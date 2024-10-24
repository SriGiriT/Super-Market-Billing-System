<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Change Password</title>
</head>
<body>
<h2>Change Password</h2>
<form action="changePassword" method="post"  onsubmit="return checkLogin(this);">
    <label for="existingPassword">Existing Password:</label>
    <input type="password" name="existingPassword" required/><br/>

    <label for="password">New Password:</label>
    <input type="password" name="password" required/><br/>

    <label for="confirmPassword">Confirm New Password:</label>
    <input type="password" name="confirmPassword" required/><br/>
	<c:if test="${not empty errorMessage }">
		<div class="error">${errorMessage }
		</div><br/>
	</c:if>
    <input type="submit" value="Change Password"/>
    <c:if test="${not empty successMessage}">
        <h4 id="successMessage" >
            <c:out value="${successMessage}"/>
        </h4>
    </c:if>
    <script type="text/javascript">
    	function checkLogin(form){
    		if(form.existingPassword.value==""){
    			alert("Please provide existing password for verfication.");
    			form.existingPassword.focus();
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
    		if(form.password.value === form.existingPassword.value){
    			alert("Password doesn't have to match the existing password!");
    			form.password.focus();
    			return false;
    		}
    		return true;
    	}
    	
    	function hideMessage() {
    		console.log("working");
            var messageElement = document.getElementById('successMessage');
            if (messageElement) {
                messageElement.style.opacity = '0';
                setTimeout(function() {
                    messageElement.style.display = 'none';
                }, 1000); 
            }
        }

        window.onload = function() {
        	console.log("onload");
            
        };
    </script>
</form>
</body>
</html>
