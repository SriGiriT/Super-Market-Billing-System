<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Cashier Page - Product Search and Cart</title>
</head>
<body>

    <h1>Welcome, ${user.userName}!</h1>

    <h2>Search Products</h2>
    <form action="searchProduct" method="post">
        <select name="searchType">
            <option value="name">Product Name</option>
            <option value="id">Product ID</option>
        </select>
        <input type="text" name="searchQuery" placeholder="Enter product name or ID" required />
        <button type="submit">Search</button>
    </form>

    <c:if test="${not empty searchResults}">
        <h2>Search Results</h2>
        <form action="CartServlet" method="post">
            <table border="1">
                <tr>
                    <th>Product Name</th>
                    <th>Price</th>
                    <th>Stock Left</th>
                    <th>Pick Quantity</th>
                </tr>
                <c:forEach var="product" items="${searchResults}">
                    <tr>
                        <td>${product.name}</td>
                        <td><fmt:formatNumber value="${product.price}" type="currency" /></td>
                        <td>${product.stockLeft}</td>
                        <td>
                            <input type="hidden" name="productIds" value="${product.id}" />
                            <input type="number" name="quantities" value="0" min="0" max="${product.stockLeft}" />
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <button type="submit">Add to Cart</button>
        </form>
    </c:if>

    <c:if test="${not empty cart.items}">
        <h2>Selected Products</h2>
            <form action="UpdateCartServlet" method="post">
        <table border="1">
            <thead>
                <tr>
                    <th>Product Name</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Edit Quantity</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="overallAmount" value="0" />
                <c:forEach var="item" items="${cart.items}">
                    <tr>
                        <td>${item.product.name}</td>
                        <td>${item.quantity}</td>
                        <td><fmt:formatNumber value="${item.product.price * item.quantity}" type="currency" /></td>
                        <td>
                            <input type="hidden" name="productIds" value="${item.product.id}" />
                            <input type="number" name="quantities" value="${item.quantity}" min="1" max="${item.product.stockLeft}" />
                        </td>
                    </tr>
                    <c:set var="overallAmount" value="${overallAmount + (item.product.price * item.quantity)}" />
                </c:forEach>
            </tbody>
        </table>
        <button type="submit">Update Cart</button>
    </form><br>
    
            <c:if test="${not empty providedOffer}">
                <div>Before Applying: <fmt:formatNumber value="${overallAmount}" type="currency" /></div>
                <div>Offer Applied: <fmt:formatNumber value="${providedOffer}" type="currency" /></div>
                <c:set var="overallAmount" value="${overallAmount - providedOffer}" />
            </c:if>
            <form action="CouponCode" method="get">
            <div>Total Amount: <fmt:formatNumber value="${overallAmount}" type="currency" /></div>
            
            <label for="couponCode">Coupon Code:</label>
            <input type="hidden" name="overallAmount" value="${overallAmount}" />
            <input type="text" id="couponCode" name="couponCode" />
            <button type="submit">Apply Coupon</button>
        </form>

        <c:if test="${not empty couponMessage}">
            <div class="coupon">${couponMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
		<c:if test="${user.role == 'CASHIER'}">
			<form action="loadUser" method="post">
				<input type="text" name="customerPhoneNumber" value="${customerPhoneNumber }"/>
				<button type="submit">Load User</button>
			</form>
				<c:if test="${not empty userNotExist }">
					<div class="error">
					<div>${userNotExist}</div><br/>
					<form action="createUser" method="post">
						<label for="customerPhoneNumber">Customer Phone Number: </label>
						<input type="text" name="customerPhoneNumber" value="${customerPhoneNumber}"/><br/>
						<label for="customerUserName">Customer Name: </label>
						<input type="text" name="customerUserName" /><br/>
						<label for="customerEmail">Customer Email: </label>
						<input type="text" name="customerEmail"/><br/>
						<input type="submit" value="Create User"/>
					 </form>
					</div>
				</c:if>
				
		</c:if>
		<c:if test="${not empty customerExist}">
			<div >${customerExist}</div><br/>
		</c:if>
        <form action="BillServlet" method="post">
            <input type="hidden" name="overallAmount" value="${overallAmount}" />
            <input type="hidden" name="providedOffer" value="${providedOffer}" />
            <c:if test="${user.role == 'CASHIER' }">
            	<input type="hidden" name="customerPhoneNumber" value="${customerPhoneNumber }">
            </c:if>
            <button type="submit">Proceed to Bill</button>
        </form>
    </c:if>

</body>
</html>
