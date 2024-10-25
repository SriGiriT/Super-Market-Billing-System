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
</head>
<body>
    <h1>Reports</h1>
    <%
    
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
    response.setHeader("Pragma", "no-cache"); 
    response.setHeader("Expires", "0"); 
	
    %>
   
    <c:choose>
        <c:when test="${user.role == 'CUSTOMER'}">
        	<ul class="report-list">
		        <li><a href="ReportServlet?action=frequentlyBoughtItem&userId=${user.id}"  target="_blank">Frequently Bought Items</a></li>
		        <li><a href="ReportServlet?action=allBillStatements&userId=${user.id}"  target="_blank">All Bill Statements</a></li>
    		</ul>	
        </c:when>
        <c:when test="${user.role == 'ADMIN'}">
        	<ul class="report-list">
		        <li><a href="ReportServlet?action=topSellingProducts" target="_blank">Top Selling Products</a></li>
		        <li><a href="ReportServlet?action=topCustomers"  target="_blank">Top 10 Customers</a></li>
		        <li><a href="ReportServlet?action=inactiveCustomers"  target="_blank">Customers Who Didn't Buy in Last Month</a></li>
		        <li><a href="ReportServlet?action=topCashier"  target="_blank">Salesperson Who Billed the Most</a></li>
		        <li><a href="ReportServlet?action=dailyRevenue"  target="_blank">Daily Revenue for the Past Week</a></li>
		        <li><a href="ReportServlet?action=signedUpNoPurchase"  target="_blank">Customers Who Signed Up but Did Not Purchase</a></li>
		        <li><a href="ReportServlet?action=lowStock"  target="_blank">Products That Need to Be Replenished</a></li>
		        <li><a href="ReportServlet?action=unsoldProducts"  target="_blank">Products That Have Not Been Sold</a></li>
		        <li><a href="ReportServlet?action=outOfStock"  target="_blank">Out of Stock Products</a></li>
    		</ul>		
        </c:when>
    </c:choose>
    <c:if test="${not empty errorMessage}">
    	<div>${errorMessage}</div>
    	<% 
    		session.removeAttribute("errorMessage");
    		request.removeAttribute("errorMessage");
    	%>
    </c:if>
</body>
</html>
