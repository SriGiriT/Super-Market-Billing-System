<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reports</title>
    <style>
        .report-list {
            list-style-type: none;
        }
        .report-list li {
            margin-bottom: 10px;
        }
    </style>
    <link rel="stylesheet" href="style_home.css"> 
</head>
<body>
<div   class="content-container" style="max-width: 600px;">
    <h1>Reports</h1><br><br>
    <%
    
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
    response.setHeader("Pragma", "no-cache"); 
    response.setHeader("Expires", "0"); 
	
    %>
   <div class="pagination">
    <c:choose>
        <c:when test="${user.role == 'CUSTOMER'}">
        	<ul class="report-list">
		        <li><a href="ReportServlet?action=frequentlyBoughtItem&userId=${user.id}"   >Frequently Bought Items</a></li><br><br>
		        <li><a href="ReportServlet?action=allBillStatements&userId=${user.id}"   >All Bill Statements</a></li><br><br>
    		</ul>	
        </c:when>
        <c:when test="${user.role == 'ADMIN'}">
        	<ul class="report-list">
		        <li><a href="ReportServlet?action=topSellingProducts"  >Top Selling Products</a></li><br><br>
		        <li><a href="ReportServlet?action=topCustomers"   >Top 10 Customers</a></li><br><br>
		        <li><a href="ReportServlet?action=inactiveCustomers"   >Customers Who Didn't Buy in Last Month</a></li><br><br>
		        <li><a href="ReportServlet?action=topCashier"   >Salesperson Who Billed the Most</a></li><br><br>
		        <li><a href="ReportServlet?action=dailyRevenue"   >Daily Revenue for the Past Week</a></li><br><br>
		        <li><a href="ReportServlet?action=signedUpNoPurchase"   >Customers Who Signed Up but Did Not Purchase</a></li><br><br>
		        <li><a href="ReportServlet?action=lowStock"   >Products That Need to Be Replenished</a></li><br><br>
		        <li><a href="ReportServlet?action=unsoldProducts"   >Products That Have Not Been Sold</a></li><br><br>
		        <li><a href="ReportServlet?action=outOfStock"   >Out of Stock Products</a></li><br><br>
    		</ul>		
        </c:when>
        <c:when test="${user.role == 'CASHIER' || user.role == 'INVENTORY_MANAGER' }">
        	<div >
        		<h1>No reports available !</h1>
        	</div>
        </c:when>
    </c:choose>
    <c:if test="${not empty errorMessage}">
    	<div>${errorMessage}</div>
    	<% 
    		session.removeAttribute("errorMessage");
    		request.removeAttribute("errorMessage");
    	%>
    </c:if>
    </div>
    </div>
</body>
</html>
