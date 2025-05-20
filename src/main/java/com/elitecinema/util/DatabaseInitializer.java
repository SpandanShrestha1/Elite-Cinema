package com.elitecinema.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 * Utility class for initializing the database
 */
public class DatabaseInitializer {
    
    /**
     * Initialize the database with required tables and test users
     * @return true if initialization was successful, false otherwise
     */
    public static boolean initializeDatabase() {
        Connection conn = null;
        Statement stmt = null;
        
        try {
            // Try to get a connection to MySQL server (without specifying a database)
            try {
                conn = DatabaseUtil.getRootConnection();
                System.out.println("Connected to MySQL server successfully");
            } catch (SQLException e) {
                System.err.println("Failed to connect to MySQL server: " + e.getMessage());
                return false;
            }
            
            stmt = conn.createStatement();
            
            // Read SQL script from file
            InputStream is = DatabaseInitializer.class.getClassLoader().getResourceAsStream("sql/init_database.sql");
            if (is == null) {
                System.err.println("Could not find init_database.sql");
                return false;
            }
            
            String sql = new BufferedReader(new InputStreamReader(is))
                    .lines().collect(Collectors.joining("\n"));
            
            // Split SQL script into individual statements
            String[] statements = sql.split(";");
            
            // Execute each statement
            for (String statement : statements) {
                if (!statement.trim().isEmpty()) {
                    System.out.println("Executing SQL: " + statement.trim());
                    stmt.execute(statement.trim());
                }
            }
            
            System.out.println("Database initialized successfully");
            return true;
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Main method to run the database initialization
     */
    public static void main(String[] args) {
        boolean success = initializeDatabase();
        System.out.println("Database initialization " + (success ? "successful" : "failed"));
    }
}
