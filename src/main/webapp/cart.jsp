<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Your Cart</title>
</head>
<body>
    <h1>Your Shopping Cart</h1>
    <c:set var="overallAmount" value="${0}"/>
    <form action="CouponCode" method="get">
    <table border="1">
        <thead>
            <tr>
                <th>Product Name</th>
                <th>Quantity</th>
                <th>Price</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${cart.items}">
                <tr>
                    <td>${item.product.name}</td>
                    <td>${item.quantity}</td>
                    <td>${item.product.price * item.quantity}</td>
                </tr>
                <c:set var="overallAmount" value="${overallAmount + item.product.price * item.quantity}" />
            </c:forEach>
        </tbody>
    </table><br>
    <c:if test="${not empty providedOffer && providedOffer != 0}">
    	<div><span>Before Applying: </span>${overallAmount }</div>
    	<div><span>Offer applied: </span>${providedOffer}</div><br>
    	<c:set var="overallAmount" value="${overallAmount - providedOffer}"/>
    </c:if>
    <div><span>Total Amount: </span>${overallAmount}</div><br>
    	<label for="couponCode">Coupon Code:</label>
    	<input type="hidden" name="overallAmount" value="${overallAmount}">
        <input type="text" id="couponCode" name="couponCode" required>
        <input type="submit" value="Apply Coupon!">
    </form>
    <c:if test="${not empty couponMessage}">
    	<div class="coupon">${couponMessage }</div>
    </c:if>
	<c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>
    <form action="BillServlet" method="post">
    	<input type="hidden" name="providedOffer" value="${providedOffer}">
    	<input type="hidden" name="overallAmount" value="${overallAmount}">
        <button type="submit">Proceed to Bill</button>
    </form>
    
</body>
</html>
