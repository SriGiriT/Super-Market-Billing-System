package com.billingsystem.utility;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {
    private  final String URL = "jdbc:mysql://localhost:3306/super_market_billing";
    private  final String USER = "root";
    private  final String PASSWORD = System.getenv("DB_SECRET_PASSWORD");
    private static DBConnectionUtil instance;
    private Connection connection;
    

    private DBConnectionUtil() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error conecting to the database", e);
        }
    }
    
    public Connection getConnection() {
    	return connection;
    }
    
    public static DBConnectionUtil getInstance() throws SQLException{
    	if(instance == null || instance.getConnection().isClosed()) {
    		synchronized(DBConnectionUtil.class) {
    			if(instance == null) {
    				instance = new DBConnectionUtil();
    			}
    		}
    	}
    	return instance;
    }
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Failed to close database connection.");
            }
        }
    }
}
