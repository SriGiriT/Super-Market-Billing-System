package com.billingsystem.service;


import com.billingsystem.DAO.ProductDao;
import com.billingsystem.Model.Product;

import java.util.List;

public class ProductService {
    private ProductDao productDao = new ProductDao();

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }
    
    public Product getProductByID(long id) {
    	return productDao.getProductByID(id);
    }
    
    public void updateProduct(Product product) {
    	productDao.updateProduct(product);
    }
    
    public void updateProductStock(Product product) {
    	productDao.updateProductStock(product);
    }
    public List<Product> getProductsByName(String name){
    	return productDao.getProductsByName(name);
    }

	public List<Product> getProductsPaginated(int startIndex, int pageSize) {
		return productDao.getProductsPaginated(startIndex, pageSize);
	}
	public int getTotalProductCount() {
		return productDao.getTotalProductCount();
	}
}
