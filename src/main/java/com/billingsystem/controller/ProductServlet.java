package com.billingsystem.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.billingsystem.utility.LoggerUtil;


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
        User user = (User) session.getAttribute("user");
        String searchType = request.getParameter("searchType"); 
        String searchQuery = request.getParameter("searchQuery");
        String role = user.getRole().toString();
        List<Product> productList = new ArrayList<>();
        if(productId == null) {
        	if(searchType != null && searchQuery != null) {
        		if("id".equals(searchType)) {
        			try {
        				productList.add(productService.getProductByID(Integer.parseInt(searchQuery)));
        			}catch(NumberFormatException e) {
        				e.printStackTrace();
        			}
        		}else if("name".equals(searchType)) {
        			productList = productService.getProductsByName(searchQuery);
        		}
        	}else {
        		
	        	int pageSize = 10; 
	        	int currentPage = 1;  
	
	        	String pageParam = request.getParameter("page");
	        	if (pageParam != null && !pageParam.isEmpty()) {
	        	    currentPage = Integer.parseInt(pageParam);
	        	}
	
	        	int startIndex = (currentPage - 1) * pageSize;
	
	        	productList = productService.getProductsPaginated(startIndex, pageSize);
	
	        	int totalProducts = productService.getTotalProductCount();
	        	int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
	
	        	request.setAttribute("currentPage", currentPage);
	        	request.setAttribute("totalPages", totalPages);
	            
        	}
        	String sortColumn = request.getParameter("sortColumn");
            String sortOrder = request.getParameter("sortOrder");
            if (sortColumn != null && sortOrder != null) {
                Comparator<Product> comparator = getComparator(sortColumn, sortOrder);
                productList.sort(comparator);
            }
            
            if(searchType != null && searchQuery != null) {
            	LoggerUtil.getInstance().getLogger().debug(productList.toString());
            	 request.setAttribute("searchResults", productList);
            }else {
            	 request.setAttribute("products", productList);
            }
            request.setAttribute("sortColumn", sortColumn);
            request.setAttribute("sortOrder", sortOrder);
            request.setAttribute("role", role);
            request.getRequestDispatcher("products.jsp").forward(request, response);
        }else {
			if(role.equals("INVENTORY_MANAGER") || role.equals("ADMIN")) {
				Product product = productService.getProductByID(Long.parseLong(productId));
				session.setAttribute("product", product);
			    request.getRequestDispatcher("/update_product.jsp").forward(request, response);
        	}
        	
        }
        
    }
    private Comparator<Product> getComparator(String sortColumn, String sortOrder) {
        Comparator<Product> comparator;

        switch (sortColumn) {
        	case "id":
        		comparator = Comparator.comparing(Product::getId); 
        		break;
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

