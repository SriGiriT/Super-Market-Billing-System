<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tags/implicit.tld" prefix="sorting" %>

<html>
<head>
    <title>Supermarket Products</title>
</head>
<body>
    <h1>Welcome, ${user.userName}!</h1>
    <h2>Product List</h2>

    <c:choose>
        <c:when test="${role == 'CUSTOMER'}">
            <form action="CartServlet" method="post">
                <table border="1">
                    <tr>
                        <th>Product Name
                            <sorting:sortIcon column="name" sortColumn="${sortColumn}" sortOrder="${sortOrder}" />
                        </th>
                        <th>Price
                            <sorting:sortIcon column="price" sortColumn="${sortColumn}" sortOrder="${sortOrder}" />
                        </th>
                        <th>Items Left
                            <sorting:sortIcon column="stockLeft" sortColumn="${sortColumn}" sortOrder="${sortOrder}" />
                        </th>
                        <th>Description
                            <sorting:sortIcon column="description" sortColumn="${sortColumn}" sortOrder="${sortOrder}" />
                        </th>
                        <th>Pick Quantity</th>
                    </tr>
                    <c:forEach var="product" items="${products}">
                        <tr>
                            <td>${product.name}</td>
                            <td><fmt:formatNumber value="${product.price}" type="currency" /></td>
                            <td>${product.stockLeft}</td>
                            <td>${product.description}</td>
                            <td>
                                <input type="hidden" name="productIds" value="${product.id}" />
                                <input type="number" name="quantities" value="0" min="0" max="${product.stockLeft}" />
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <button type="submit">Add to Cart</button>
            </form>
        </c:when>
        <c:when test="${role == 'CASHIER'}">
            
        </c:when>

        <c:when test="${role == 'INVENTORY_MANAGER'}">
            <table border="1">
                <tr>
                    <th>Product Name</th>
                    <th>Price</th>
                    <th>Stock Left</th>
                    <th>Usual Stock</th>
                    <th>Update Product</th>
                </tr>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.name}</td>
                        <td><fmt:formatNumber value="${product.price}" type="currency" /></td>
                        <td>${product.stockLeft}</td>
                        <td>${product.usualStock}</td>
                        <td><a href="products?productId=${product.id}">Update Product</a></td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>

        <c:when test="${role == 'ADMIN'}">
            <table border="1">
                <tr>
                    <th>Product Name</th>
                    <th>Price</th>
                    <th>Stock Left</th>
                    <th>Usual Stock</th>
                    <th>Cost Price</th>
                    <th>Profit</th>
                    <th>Total Sold</th>
                </tr>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.name}</td>
                        <td><fmt:formatNumber value="${product.price}" type="currency" /></td>
                        <td>${product.stockLeft}</td>
                        <td>${product.usualStock}</td>
                        <td>${product.buyerPrice}</td>
                        <td><fmt:formatNumber value="${product.price - product.buyerPrice}" type="currency" /></td>
                        <td>${product.totalSold}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
    </c:choose>

    <!-- Cart Section (For Customer Role) -->
    <c:if test="${role == 'CUSTOMER' && not empty cart.items}">
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

        <!-- Offer Section -->
        <c:if test="${not empty providedOffer}">
            <div>Before Applying: <fmt:formatNumber value="${overallAmount}" type="currency" /></div>
            <div>Offer Applied: <fmt:formatNumber value="${providedOffer}" type="currency" /></div>
            <c:set var="overallAmount" value="${overallAmount - providedOffer}" />
        </c:if>
        
        <!-- Coupon Section -->
        <form action="CouponCode" method="get">
            <div>Total Amount: <fmt:formatNumber value="${overallAmount}" type="currency" /></div>
            
            <label for="couponCode">Coupon Code:</label>
            <input type="hidden" name="overallAmount" value="${overallAmount}" />
            <input type="text" id="couponCode" name="couponCode" value="${appliedCouponCode}" />
            <button type="submit">Apply Coupon</button>
        </form>

        <!-- Messages Section -->
        <c:if test="${not empty couponMessage}">
            <div class="coupon">${couponMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>

        <!-- Billing Section -->
        <form action="BillServlet" method="post">
            <input type="hidden" name="overallAmount" value="${overallAmount}" />
            <input type="hidden" name="providedOffer" value="${providedOffer}" />
            <button type="submit">Proceed to Bill</button>
        </form>
    </c:if>
</body>
</html>
