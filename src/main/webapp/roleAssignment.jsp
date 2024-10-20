<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Assign Roles</title>
</head>
<body>
    <h2>Assign Roles</h2>
    <form action="assignRole" method="post">
        Mobile Number: <input type="text" name="mobileNumber" required/><br/>
        Role:
        <select name="role">
            <option value="CASHIER">Cashier</option>
            <option value="INVENTORY_MANAGER">Inventory Manager</option>
            <option value="ADMIN">Admin</option>
        </select><br/>
        <input type="submit" value="Assign Role"/>
    </form>
    <c:if test="${not empty message}">
        <p style="color:green">${message}</p>
    </c:if>
</body>
</html>
