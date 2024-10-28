package com.billingsystem.utility;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionPoolUtil {

    private static HikariDataSource dataSource;
    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/super_market_billing"); 
        config.setUsername("root"); 
        config.setPassword(System.getenv("DB_SECRET_PASSWORD")); 
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        config.setMaximumPoolSize(20); 
        config.setMinimumIdle(5); 
        config.setIdleTimeout(30000);
        config.setMaxLifetime(1800000); 
        config.setConnectionTimeout(20000); 

        config.setLeakDetectionThreshold(15000); 

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
