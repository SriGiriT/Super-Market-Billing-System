<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Change Password</title>
</head>
<body>
<h2>Change Password</h2>
<form action="changePassword" method="post">
    <label for="newPassword">New Password:</label>
    <input type="password" name="newPassword" required/><br/>

    <input type="submit" value="Change Password"/>
</form>
</body>
</html>
