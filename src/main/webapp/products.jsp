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
                        <td>${product.description }</td>
                        <td>
                            <input type="hidden" name="productIds" value="${product.id}" />
                            <input type="number" name="quantities" value="0" min="0" max="${product.stockLeft}" />
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <button type="submit">Add to cart</button>
            </form>
        </c:when>
        <c:when test="${role == 'CASHIER'}">
            <table border="1">
                <tr>
                    <th>Product Name</th>
                    <th>Price</th>
                    <th>Stock Left</th>
                    <th>Bill Item</th>
                </tr>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.name}</td>
                        <td><fmt:formatNumber value="${product.price}" type="currency" /></td>
                         <td>${product.stockLeft}</td>
                        <td><a href="addToBill?productId=${product.id}">Add to Bill</a></td>
                    </tr>
                </c:forEach>
            </table>
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
</body>
</html>
