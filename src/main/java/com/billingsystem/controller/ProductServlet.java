package com.billingsystem.controller;

import java.io.IOException;
import java.util.Comparator;
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


@WebServlet("/products")
public class ProductServlet extends HttpServlet {
	
    private static final long serialVersionUID = 1L;
	private ProductService productService = new ProductService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String productId = request.getParameter("productId");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
        response.setHeader("Pragma", "no-cache"); 
        response.setHeader("Expires", "0"); 
        if(productId == null) {
        	User user = (User) session.getAttribute("user");
            String role = user.getRole().toString();
            List<Product> products = productService.getAllProducts();
            String sortColumn = request.getParameter("sortColumn");
            String sortOrder = request.getParameter("sortOrder");
            if (sortColumn != null && sortOrder != null) {
                Comparator<Product> comparator = getComparator(sortColumn, sortOrder);
                products.sort(comparator);
            }
            request.setAttribute("products", products);
            request.setAttribute("sortColumn", sortColumn);
            request.setAttribute("sortOrder", sortOrder);
            request.setAttribute("role", role);
            request.getRequestDispatcher("products.jsp").forward(request, response);
           
        }else {
        	Product product = productService.getProductByID(Long.parseLong(productId));
    		request.setAttribute("product", product);
    		session.setAttribute("product", product);
            request.getRequestDispatcher("/update_product.jsp").forward(request, response);
        }
        
    }
    private Comparator<Product> getComparator(String sortColumn, String sortOrder) {
        Comparator<Product> comparator;

        switch (sortColumn) {
            case "name":
                comparator = Comparator.comparing(Product::getName);
                break;
            case "description":
                comparator = Comparator.comparing(Product::getDescription);
                break;
            case "price":
                comparator = Comparator.comparing(Product::getPrice).thenComparing(Product::getName);
                break;
            case "stockLeft":
                comparator = Comparator.comparing(Product::getStockLeft);
                break;
            default:
                comparator = Comparator.comparing(Product::getName); 
        }

        if ("desc".equals(sortOrder)) {
            comparator = comparator.reversed();
        }

        return comparator;
    }
}

