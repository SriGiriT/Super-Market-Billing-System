package com.billingsystem.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.billingsystem.Model.Product;
import com.billingsystem.Model.User;
import com.billingsystem.service.ProductService;


@WebServlet("/updateProduct")
public class UpdateProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService = new ProductService();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = (User)session.getAttribute("user");
		if(user.getRole().toString() == "INVENTORY_MANAGER") {
			Product product = (Product) session.getAttribute("product");
			String productName = request.getParameter("name");
			String description = request.getParameter("description");
			double price = Double.parseDouble(request.getParameter("sellingPrice"));
			int stockLeft = Integer.parseInt(request.getParameter("stockLeft"));
			int usualStock = Integer.parseInt(request.getParameter("usualStock"));
			product.setName(productName);
			product.setDescription(description);
			product.setPrice(price);
			product.setStockLeft(stockLeft);
			product.setUsualStock(usualStock);
			if(product != null) {
				productService.updateProduct(product);
			}
			List<Product> products = productService.getAllProducts();
			request.setAttribute("products", products);
			request.setAttribute("role", "INVENTORY_MANAGER");
			request.getRequestDispatcher("/products.jsp").forward(request, response);
		}
	}

}
