<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tags/implicit.tld" prefix="sorting" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Supermarket Products</title>
    <link rel="stylesheet" type="text/css" href="style_home.css">
</head>
<body style="padding-top:0px">
	<div style="display:flex;justify-content:space-between;width: 1500px;padding: 10px;" class="content-container">
	<div class="product-section" style="width:1200px">
	<span>Search Products</span>
    <form action="products" style="display:contents;">
        <select name="searchType" style="width:auto;">
            <option value="name">Product Name</option>
            <option value="id">Product ID</option>
        </select>
        <input type="text"  style="width:auto;" name="searchQuery" class="input-field" placeholder="Enter product name or ID" required />
        <button type="submit" class="button-submit">Search</button>
    </form>
	
    <c:if test="${not empty searchResults}">
        <h2>Search Results</h2>
        <form action="CartServlet" method="post">
            <table border="1" class="product-table">
                <tr>
                		
                 		<th>Product ID
                        </th>
                    	<th>Product Name
                        </th>
                        <th>Price
                        </th>
                        <th>Items Left
                        </th>
                        <c:if test="${user.role == 'ADMIN' }">
                        	<th>Usual Stock</th>
                        	<th>Cost Price</th>
                        	<th>Profit</th>
                        	<th>Total Sold</th>
                        	<th>Update Product</th>
                        </c:if>
                        <c:if test="${user.role== 'INVENTORY_MANAGER' }">
                        	<th>Cost Price</th>
                        	<th>Update Product</th>
                        </c:if>
                        <c:if test="${user.role == 'CUSTOMER' || user.role == 'CASHIER' }">
                        	<th>Description
                        	</th>
                        </c:if>
                        <c:if test="${ user.role != 'ADMIN'}">
                        	<th>Pick Quantity</th>
                        </c:if>
                        
                </tr>
                <c:forEach var="product" items="${searchResults}">
                    <tr>
                    	<td>${product.id}</td>
                        <td>${product.name}</td>
                        <td>&#8377; <fmt:formatNumber value="${product.price}" type="currency"   currencySymbol="₹" pattern="#,##0.00"/></td>
                        <td>${product.stockLeft}</td>
                        <c:if test="${user.role == 'CUSTOMER' || user.role == 'CASHIER' }">
                        		<td>${product.description}</td>
	                        </c:if>
	                        <c:if test="${user.role== 'INVENTORY_MANAGER'}">
                            	<td>&#8377; <fmt:formatNumber value="${product.buyerPrice}" type="currency"   currencySymbol="₹" pattern="#,##0.00"/></td>
                        		<td><a href="products?productId=${product.id}">Update Product</a></td>
                        	</c:if>
                        <c:if test="${user.role != 'ADMIN' }">
	                        <td>
	                            <input type="hidden" name="productIds" value="${product.id}" />
	                            <input type="number" name="quantities" class="input-quantity"  value="0" min="0" max="${product.stockLeft}" />
	                        </td>
                        </c:if>
                        <c:if test="${user.role == 'ADMIN' }">
	                        <td>${ product.usualStock}</td>
	                        <td>&#8377; <fmt:formatNumber value="${product.buyerPrice}" type="currency"   currencySymbol="₹" pattern="#,##0.00"/></td>
	                        <td>&#8377; <fmt:formatNumber value="${product.price - product.buyerPrice}" type="currency"  currencySymbol="₹" pattern="#,##0.00"/></td>
	                        <td>${product.totalSold}</td>
	                        <td><a href="products?productId=${product.id}">Update Product</a></td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
            <c:if test="${user.role != 'ADMIN'}">
            	<button type="submit" class="button-submit">Add to Cart</button>
            </c:if>
        </form>
    </c:if>
    <c:choose>
        <c:when test="${user.role == 'INVENTORY_MANAGER' || user.role == 'CASHIER' || user.role== 'CUSTOMER'}">
        	<c:if test="${empty searchResults}">
            <form action="CartServlet" method="post">
                <table border="1"  class="product-table">
                    <tr>
                    	<th>Product ID
                            <sorting:sortIcon column="id" sortColumn="${sortColumn}" sortOrder="${sortOrder}" />
                        </th>
                        <th>Product Name
                            <sorting:sortIcon column="name" sortColumn="${sortColumn}" sortOrder="${sortOrder}" />
                        </th>
                        <th>Price
                            <sorting:sortIcon column="price" sortColumn="${sortColumn}" sortOrder="${sortOrder}" />
                        </th>
                        <th>Items Left
                            <sorting:sortIcon column="stockLeft" sortColumn="${sortColumn}" sortOrder="${sortOrder}" />
                        </th>
                        <c:if test="${user.role== 'INVENTORY_MANAGER' }">
                        	<th>Cost Price</th>
                        	<th>Update Product</th>
                        </c:if>
                        <c:if test="${user.role == 'CUSTOMER' || user.role == 'CASHIER' }">
                        	<th>Description
                            <sorting:sortIcon column="description" sortColumn="${sortColumn}" sortOrder="${sortOrder}" />
                        </th>
                        </c:if>
                        <th>Pick Quantity</th>
                        
                        
                    </tr>
                    <c:forEach var="product" items="${products}">
                        <tr>
                        	<td>${product.id}</td>
                            <td>${product.name}</td>
                            <td>&#8377; <fmt:formatNumber value="${product.price}" type="currency"   currencySymbol="₹" pattern="#,##0.00"/></td>
                            <td>${product.stockLeft}</td>
                            <c:if test="${user.role == 'CUSTOMER' || user.role == 'CASHIER' }">
                        		<td>${product.description}</td>
	                        </c:if>
	                        <c:if test="${user.role== 'INVENTORY_MANAGER' }">
                            	<td>&#8377; <fmt:formatNumber value="${product.buyerPrice}" type="currency"   currencySymbol="₹" pattern="#,##0.00"/></td>
                        		<td><a href="products?productId=${product.id}">Update Product</a></td>
                        	</c:if>
                            <td>
                                <input type="hidden" name="productIds" value="${product.id}" />
                                <input type="number" name="quantities" value="0" min="0" max="${user.role=='INVENTORY_MANAGER' ? product.usualStock : product.stockLeft}" onchange="showAddToCartButton()" />
                            </td>
                            
                        </tr>
                    </c:forEach>
                </table>
                <button type="submit" class="button-submit" style="margin-right: 300px;display:none;" id="addToCartButton">
                <c:if test="${user.role=='INVENTORY_MANAGER'}">
                      Add to List
                </c:if>
                <c:if test="${user.role == 'CUSTOMER' || user.role == 'CASHIER' }">
                      Add to Cart
                </c:if>
                </button>
                <div class="pagination" style="display:initial;position:sticky;left:500px;">
                <c:if test="${currentPage > 1}">
                    <a href="?page=${currentPage - 1}">Previous</a>
                </c:if>

                <c:forEach var="page" begin="1" end="${totalPages}">
                    <c:choose>
                        <c:when test="${page == currentPage}">
                            <strong>${page}</strong>
                        </c:when>
                        <c:otherwise>
                            <a href="?page=${page}">${page}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <a href="?page=${currentPage + 1}">Next</a>
                </c:if>
            </div>
            </form>
            </c:if>
        </c:when>
        <c:when test="${user.role == 'ADMIN'}">
        <c:if test="${empty searchResults}">
        	<h2>Product List</h2>
            <table border="1"  class="product-table">
                <tr>
                	<th>Product ID</th>
                    <th>Product Name</th>
                    <th>Price</th>
                    <th>Stock Left</th>
                    <th>Usual Stock</th>
                    <th>Cost Price</th>
                    <th>Profit</th>
                    <th>Total Sold</th>
                    <th>Update Product</th>
                </tr>
                <c:forEach var="product" items="${products}">
                    <tr>
                    	<td>${product.id }</td>
                        <td>${product.name}</td>
                        <td>&#8377; <fmt:formatNumber value="${product.price}" type="currency"   currencySymbol="&#8377;" pattern="#,##0.00"/></td>
                        <td>${product.stockLeft}</td>
                        <td>${product.usualStock}</td>
                        <td>&#8377; <fmt:formatNumber value="${product.buyerPrice}" type="currency"  currencySymbol="₹" pattern="#,##0.00"/></td>
                        <td>&#8377; <fmt:formatNumber value="${product.price - product.buyerPrice}" type="currency"  currencySymbol="₹" pattern="#,##0.00"/></td>
                        <td>${product.totalSold}</td>
                        <td><a href="products?productId=${product.id}">Update Product</a></td>
                    </tr>
                </c:forEach>
            </table>
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="?page=${currentPage - 1}">Previous</a>
                </c:if>

                <c:forEach var="page" begin="1" end="${totalPages}">
                    <c:choose>
                        <c:when test="${page == currentPage}">
                            <strong>${page}</strong>
                        </c:when>
                        <c:otherwise>
                            <a href="?page=${page}">${page}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <a href="?page=${currentPage + 1}">Next</a>
                </c:if>
            </div>
            </c:if>
        </c:when>
    </c:choose>
	
    </div>
    <div class="cart-section">
    <c:if test="${not empty cart.items}">
        <h2>Selected Products</h2>
        <form action="UpdateCartServlet" method="post">
            <table border="1"  class="cart-table">
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
                            <td>&#8377; <fmt:formatNumber value="${user.role == 'INVENTORY_MANAGER' ? item.product.buyerPrice * item.quantity : item.product.price * item.quantity}" type="currency"   currencySymbol="₹" pattern="#,##0.00"/></td>
                            <td>
                                <input type="hidden" name="productIds" value="${item.product.id}" />
                                <input type="number" name="quantities" class="input-quantity"  value="${item.quantity}" min="1" max="${user.role == 'INVENTORY_MANAGER' ? item.product.usualStock : item.product.stockLeft }" onchange="showUpdateButton()"/>
                            </td>
                        </tr>
                        <c:set var="overallAmount" value="${overallAmount + (user.role == 'INVENTORY_MANAGER' ? item.product.buyerPrice * item.quantity : item.product.price * item.quantity)}" />
                    </c:forEach>
                </tbody>
            </table>
            <button type="submit" class="button-submit" style="display:none;" id="updateCartButton">
                <c:if test="${user.role=='INVENTORY_MANAGER'}">
                      Update List
                </c:if>
                <c:if test="${user.role == 'CUSTOMER' || user.role == 'CASHIER' }">
                      Update Cart
                </c:if>
                </button>
        </form><br>
		<c:if test="${user.role != 'INVENTORY_MANAGER' }">
        <c:if test="${not empty providedOffer}">
            <div>Before Applying: &#8377; <fmt:formatNumber value="${overallAmount}" type="currency"   currencySymbol="₹" pattern="#,##0.00"/></div>
            <div>Offer Applied: &#8377; <fmt:formatNumber value="${providedOffer}" type="currency"   currencySymbol="₹" pattern="#,##0.00"/></div>
            <c:set var="overallAmount" value="${overallAmount - providedOffer}" />
        </c:if>
        
        <form action="CouponCode" method="get">
            <div>Total Amount: &#8377; <fmt:formatNumber value="${overallAmount}" type="currency"   currencySymbol="₹" pattern="#,##0.00"/></div>
            
            <label for="couponCode">Coupon Code:</label>
            <input type="hidden" name="overallAmount" value="${overallAmount}" />
            <input type="text" id="couponCode" name="couponCode"  value="${appliedCouponCode}" oninput="showApplyCouponButton()"/>
            <button type="submit" class="button-submit" style="display:none;" id="updateApplyCouponButton">Apply Coupon</button>
        </form>

        <c:if test="${not empty couponMessage}">
            <div class="coupon">${couponMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
		<c:if test="${user.role == 'CASHIER'}">
			<form action="loadUser" >
				<input type="text" name="customerPhoneNumber" value="${customerPhoneNumber }" required pattern="\d{10}" title="Please enter a 10-digit phone number."/>
				<button type="submit">Load User</button>
			</form>
				<c:if test="${not empty userNotExist }">
					<div class="error">
					<div>${userNotExist}</div><br/>
					<form action="createUser" onsubmit="return checkLogin(this);">
						<label for="customerPhoneNumber">Customer Phone Number: </label>
						<input type="text" name="customerPhoneNumber" value="${customerPhoneNumber}"/><br/>
						<label for="customerUserName">Customer Name: </label>
						<input type="text" name="customerUserName" /><br/>
						<label for="customerEmail">Customer Email: </label>
						<input type="text" name="customerEmail"/><br/>
						<input type="submit" value="Create User"/>
					 </form>
					 <c:if test="${not empty errorInCreateUser}">
					 	<div>${errorInCreateUser}</div>
					 </c:if>
					</div>
				</c:if>
				<c:if test="${not empty customerExist}">
					<div >${customerExist}</div><br/>
				</c:if>
		</c:if>
		</c:if>
        <form action="BillServlet" method="post">
            <input type="hidden" name="overallAmount" value="${overallAmount}" />
            <input type="hidden" name="providedOffer" value="${providedOffer}" />
            <br/><button type="submit" class="button-submit">
           	 <c:if test="${user.role=='INVENTORY_MANAGER'}">
                      Generate List
             </c:if>
             <c:if test="${user.role == 'CUSTOMER' || user.role == 'CASHIER' }">
                      Proceed to Bill
             </c:if>
            
            </button>
        </form>
    </c:if>
    </div>
    </div>
    <script type="text/javascript">
    function showUpdateButton() {
        document.getElementById("updateCartButton").style.display = "inline-block";
    }
    function showApplyCouponButton(){
    	document.getElementById("updateApplyCouponButton").style.display = "inline-block";
    }
    function showAddToCartButton(){
    	document.getElementById("addToCartButton").style.display = "inline-block";
    }
    </script>
</body>
</html>
