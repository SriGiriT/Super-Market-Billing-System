package com.billingsystem.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.billingsystem.Model.Cart;
import com.billingsystem.Model.Product;
import com.billingsystem.service.ProductService;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String[] productIds = request.getParameterValues("productIds");
        String[] quantities = request.getParameterValues("quantities");

        ProductService productService = new ProductService();

        for (int i = 0; i < productIds.length; i++) {
            int productId = Integer.parseInt(productIds[i]);
            int quantity = Integer.parseInt(quantities[i]);

            if (quantity > 0) { 
                Product product = productService.getProductByID(productId);
                cart.addItem(product, quantity);
            }
        }

        response.sendRedirect("cart.jsp");
    }
}
