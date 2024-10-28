<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Assign Roles</title>
    <script>
        function updateExistingRole() {
            const mobileNumberSelect = document.getElementById("mobileNumber");
            const existingRoleField = document.getElementById("existingRole");
            const roleSelect = document.getElementById("roleSelect");
            const selectedMobileNumber = mobileNumberSelect.value;

            const mobileRoles = {
                <c:forEach var="entry" items="${userAndRole}">
                    "<c:out value='${entry.key}'/>": "<c:out value='${entry.value}'/>",
                </c:forEach>
            };

            const existingRole = mobileRoles[selectedMobileNumber] || '';
            existingRoleField.value = existingRole;

            const allRoles = ["CUSTOMER", "ADMIN", "CASHIER", "INVENTORY_MANAGER"];
            roleSelect.innerHTML = ''; 

            allRoles.forEach(role => {
                if (role !== existingRole) {
                    const option = document.createElement("option");
                    option.value = role;
                    option.text = role.charAt(0) + role.slice(1).toLowerCase().replace(/_/g, ' ');
                    roleSelect.add(option);
                }
            });
        }
    </script>
    <link rel="stylesheet" href="style_home.css"> 
</head>
<body>
<div   class="content-container" style="max-width: 600px;">
    <h2>Assign Roles</h2>
    <form action="assignRole" method="post">
        Mobile Number:
        <select id="mobileNumber" name="mobileNumber" onchange="updateExistingRole()" required>
            <option value="">Select Mobile Number</option>
            <c:forEach var="entry" items="${userAndRole}">
                <option value="<c:out value='${entry.key}'/>"><c:out value='${entry.key}'/></option>
            </c:forEach>
        </select><br/>

        Existing Role:
        <input type="text" id="existingRole" name="existingRole" readonly/><br/>

        Update to Role:
        <select id="roleSelect" name="role" required>
        </select><br/>

        <input type="submit" value="Assign Role"/>
    </form>
    <c:if test="${not empty message}">
        <p style="color:green">${message}</p>
    </c:if>
 </div>
</body>
</html>
