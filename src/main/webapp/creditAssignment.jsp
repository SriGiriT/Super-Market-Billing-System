<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Update Credit</title>
    <script>
        function updateExistingCredit() {
            const mobileNumberSelect = document.getElementById("mobileNumber");
            const existingCreditField = document.getElementById("existingCredit");
            const creditInputField = document.getElementById("creditInput");
      		
            const selectedMobileNumber = mobileNumberSelect.value;

            const mobileCredits = {
                <c:forEach var="entry" items="${userAndCredit}">
                    "<c:out value='${entry.key}'/>": <c:out value='${entry.value}'/>,
                </c:forEach>
            };

            const existingCredit = mobileCredits[selectedMobileNumber] || 0.00;
            existingCreditField.value = existingCredit.toFixed(2); 
            creditInputField.value = existingCredit.toFixed(2);
        }
    </script>
</head>
<body>
    <h2>Update Credit</h2>
    <form action="updateCredit" method="post">
        Mobile Number:
        <select id="mobileNumber" name="mobileNumber" onchange="updateExistingCredit()" required>
            <option value="">Select Mobile Number</option>
            <c:forEach var="entry" items="${userAndCredit}">
                <option value="<c:out value='${entry.key}'/>"><c:out value='${entry.key}'/></option>
            </c:forEach>
        </select><br/>

        Existing Credit:
        <input type="text" id="existingCredit" name="existingCredit" readonly/><br/>

        Update Credit:
        <input type="number" id="creditInput" name="credit" step="10.00" min="0" max="10000" required/><br/>

        <input type="submit" value="Update Credit"/>
    </form>
    <c:if test="${not empty message}">
        <p style="color:green">${message}</p>
    </c:if>
</body>
</html>
