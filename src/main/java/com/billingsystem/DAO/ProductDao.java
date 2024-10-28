package com.billingsystem.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.billingsystem.Model.Product;
import com.billingsystem.utility.DBConnectionPoolUtil;

public class ProductDao {
        
    
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try(Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("SELECT * FROM products")	){
        	ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setStockLeft(rs.getInt("stock_left"));
                product.setBuyerPrice(rs.getDouble("seller_price"));
                product.setUsualStock(rs.getInt("usual_stock"));
                product.setTotalSold(rs.getInt("total_sold_out"));
                product.setDescription(rs.getString("description"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }


	public Product getProductByID(long id) {
		try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("Select * from products where id=?")	){
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setStockLeft(rs.getInt("stock_left"));
                product.setUsualStock(rs.getInt("usual_stock"));
                product.setBuyerPrice(rs.getDouble("seller_price"));
                product.setTotalSold(rs.getInt("total_sold_out"));
                product.setDescription(rs.getString("description"));
				return product;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Product> getProductsByName(String name){
		List<Product> products = new ArrayList<>();
        try(Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("SELECT * FROM products WHERE LOWER(name) LIKE CONCAT('%', LOWER(?), '%')")	){
        	ps.setString(1, name);
        	ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setStockLeft(rs.getInt("stock_left"));
                product.setBuyerPrice(rs.getDouble("seller_price"));
                product.setUsualStock(rs.getInt("usual_stock"));
                product.setTotalSold(rs.getInt("total_sold_out"));
                product.setDescription(rs.getString("description"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
	}
	
	  public void updateProduct(Product product) {

	        try (Connection connection = DBConnectionPoolUtil.getConnection();
	        		PreparedStatement ps = connection.prepareStatement("UPDATE products SET name = ?, description = ?, price = ?, stock_left = ?, usual_stock = ? WHERE id = ?")	) {
	            ps.setString(1, product.getName());
	            ps.setString(2, product.getDescription());
	            ps.setDouble(3, product.getPrice());
	            ps.setInt(4, product.getStockLeft());
	            ps.setInt(5, product.getUsualStock());
	            ps.setLong(6, product.getId());
	            ps.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	  
	  public void updateProductStock(Product product) {
		  try (Connection connection = DBConnectionPoolUtil.getConnection();
	        		PreparedStatement ps = connection.prepareStatement("UPDATE products SET stock_left=? WHERE id=?")	) {
	            ps.setInt(1, product.getStockLeft());
	            ps.setLong(2, product.getId());
	            ps.executeUpdate();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	  }


	public List<Product> getProductsPaginated(int startIndex, int pageSize) {
		List<Product> products = new ArrayList<>();
        try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("WITH PaginatedProducts AS ( "
            			+ "    SELECT *, ROW_NUMBER() OVER (ORDER BY id) AS row_num "
            			+ "    FROM Products "
            			+ ") "
            			+ "SELECT *  "
            			+ "FROM PaginatedProducts "
            			+ "WHERE row_num BETWEEN ? AND ?;")	){
        	ps.setInt(1, startIndex+1);
        	ps.setInt(2, startIndex+ pageSize);
        	ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setStockLeft(rs.getInt("stock_left"));
                product.setBuyerPrice(rs.getDouble("seller_price"));
                product.setUsualStock(rs.getInt("usual_stock"));
                product.setTotalSold(rs.getInt("total_sold_out"));
                product.setDescription(rs.getString("description"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
	}
	
	public int getTotalProductCount() {
        try (Connection connection = DBConnectionPoolUtil.getConnection();
        		PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS total_products FROM products;")	){
        	ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_products");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
	}
}
