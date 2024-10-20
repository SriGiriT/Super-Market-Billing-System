package com.billingsystem.utility;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {
    private  final String URL = "jdbc:mysql://localhost:3306/super_market_billing";
    private  final String USER = "root";
    private  final String PASSWORD = "G@$3";
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
    	if(instance == null) {
    		instance = new DBConnectionUtil();
    	}else if(instance.getConnection().isClosed()){
    		instance = new DBConnectionUtil();
    	}
    	return instance;
    }
}
