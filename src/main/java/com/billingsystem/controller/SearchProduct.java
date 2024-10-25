package com.billingsystem.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.billingsystem.Model.Product;
import com.billingsystem.service.ProductService;

@WebServlet("/searchProduct")
public class SearchProduct extends HttpServlet {


	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchType = request.getParameter("searchType"); 
        String searchQuery = request.getParameter("searchQuery");
        ProductService productService = new ProductService();
        List<Product> productList = productService.getAllProducts();
        List<Product> searchResults = new ArrayList<>(); 

        if ("id".equals(searchType)) {
            try {
                int productId = Integer.parseInt(searchQuery);
                productList.stream()
                        .filter(p -> p.getId() == productId)
                        .forEach(searchResults::add);
            } catch (NumberFormatException e) {
            	e.printStackTrace();
            }
        } else if ("name".equals(searchType)) {
            productList.stream()
                    .filter(p -> p.getName().toLowerCase().contains(searchQuery.toLowerCase()))
                    .forEach(searchResults::add);
        }

        request.setAttribute("searchResults", searchResults);
        request.getRequestDispatcher("products.jsp").forward(request, response);
    }
}

