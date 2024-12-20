<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Change Password</title>
    <link rel="stylesheet" href="style_home.css"> 
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    
</head>
<body style="color:white;background-image:url('https://images.pexels.com/photos/264636/pexels-photo-264636.jpeg');
   	background-size: cover;backdrop-filter: blur(5px);        
    background-position: center;   
    background-repeat: no-repeat;">
<div class="content-container" style="max-width: 600px;background: rgba( 0, 0, 0, 0.8 );
box-shadow: 0 8px 32px 0 rgba( 31, 38, 135, 0.37 );
backdrop-filter: blur( 20px );
-webkit-backdrop-filter: blur( 20px );
border-radius: 10px;
border: 1px solid rgba( 255, 255, 255, 0.18 );" >
<h2 style="color:white;">Change Password</h2>
<c:if test="${not empty NeedToChange}">
	<div>${NeedToChange}</div>
</c:if>
<form action="changePassword" method="post"  onsubmit="return checkLogin(this);">
	<c:if test="${user == null}">
		<label for="phoneNumber">Phone Number :</label>
    	<input type="text" name="phoneNumber" pattern="\d{10}" title="Please enter a 10-digit phone number."/><br/>
	</c:if>
    <label for="existingPassword">Existing Password:</label>
    <div class="password-container">
                <input type="password" id="existingPassword" name="existingPassword" required>
                <button type="button" class="toggle-password" onclick="togglePassword('existingPassword')">
                    <i class="fas fa-eye" id="existingPasswordIcon"></i>
                </button>
            </div>

    <label for="password">New Password:</label>
    <div class="password-container">
                <input type="password" id="password" name="password" required>
                <button type="button" class="toggle-password" onclick="togglePassword('password')">
                    <i class="fas fa-eye" id="passwordIcon"></i>
                </button>
            </div>

    <label for="confirmPassword">Confirm New Password:</label>
    <div class="password-container">
                <input type="password" id="confirmPassword" name="confirmPassword" required>
                <button type="button" class="toggle-password" onclick="togglePassword('confirmPassword')">
                    <i class="fas fa-eye" id="confirmPasswordIcon"></i>
                </button>
            </div>
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
    </div>
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

    	function togglePassword(elementId) {
          	 const passwordField = document.getElementById(elementId);
               const toggleIcon = document.getElementById(elementId+"Icon");
               if (passwordField.type === "password") {
                   passwordField.type = "text";
                   toggleIcon.classList.remove("fa-eye");
                   toggleIcon.classList.add("fa-eye-slash");
               } else {
                   passwordField.type = "password";
                   toggleIcon.classList.remove("fa-eye-slash");
                   toggleIcon.classList.add("fa-eye");
               }
          }
        
    </script>
</form>
</body>
</html>
