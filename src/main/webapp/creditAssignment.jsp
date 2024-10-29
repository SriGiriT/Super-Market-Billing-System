<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
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
    <link rel="stylesheet" href="style_home.css"> 
</head>
<body style="background-image:url('https://images.pexels.com/photos/264636/pexels-photo-264636.jpeg');
   	background-size: cover;color:white;        
    background-position: center;backdrop-filter: blur(5px);   
    background-repeat: no-repeat;">
<div class="content-container" style="max-width:600px;background: rgba( 0, 0, 0, 0.8 );
box-shadow: 0 8px 32px 0 rgba( 31, 38, 135, 0.37 );
backdrop-filter: blur( 20px );
-webkit-backdrop-filter: blur( 20px );
border-radius: 10px;
border: 1px solid rgba( 255, 255, 255, 0.18 );">
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
        <input type="number" id="creditInput" name="credit" min="0" max="10000" required/><br/>

        <input type="submit" value="Update Credit"/>
    </form>
    <c:if test="${not empty message}">
        <p style="color:green">${message}</p>
    </c:if>
    </div>
</body>
</html>
