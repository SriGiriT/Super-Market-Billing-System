<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Product</title>
</head>
<body>
	 <h3>Product Details</h3>
    <form action="updateProduct" method="POST">
        <input type="hidden" name="productId" value="${product.id}" />
        
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="${product.name}" required>
        <br>
        
        <label for="description">Description:</label>
        <textarea id="description" name="description" required>${product.description}</textarea>
        <br>
        
        <label for="sellingPrice">Selling Price:</label>
        <input type="text" id="sellingPrice" name="sellingPrice" value="${product.price}" required>
        <br>
        
        <label for="stockLeft">Stock Left:</label>
        <input type="number" id="stockLeft" name="stockLeft" value="${product.stockLeft}" required>
        <br>

        <label for="usualStock">Usual Stock:</label>
        <input type="text" id="usualStock" name="usualStock" value="${product.usualStock}" required>
        <br>

        <button type="submit">Update Product</button>
    </form>
</body>
</html>