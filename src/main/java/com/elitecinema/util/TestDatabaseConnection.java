package com.elitecinema.util;

import com.elitecinema.dao.UserDAO;
import com.elitecinema.dao.UserDAOImpl;
import com.elitecinema.model.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Utility class for testing database connection and user authentication
 */
public class TestDatabaseConnection {
    
    public static void main(String[] args) {
        System.out.println("Testing database connection and user authentication...");
        
        // Test database connection
        testDatabaseConnection();
        
        // Test database initialization
        testDatabaseInitialization();
        
        // Test user authentication
        testUserAuthentication();
    }
    
    private static void testDatabaseConnection() {
        System.out.println("\n=== Testing Database Connection ===");
        try {
            Connection conn = DatabaseUtil.getConnection();
            System.out.println("Database connection successful!");
            conn.close();
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testDatabaseInitialization() {
        System.out.println("\n=== Testing Database Initialization ===");
        
        // Check if database exists
        boolean dbExists = DatabaseUtil.checkDatabaseExists();
        System.out.println("Database exists: " + dbExists);
        
        // Check if users table exists
        boolean usersTableExists = DatabaseUtil.checkUsersTableExists();
        System.out.println("Users table exists: " + usersTableExists);
        
        // Initialize database if needed
        if (!dbExists || !usersTableExists) {
            System.out.println("Initializing database...");
            boolean initialized = DatabaseInitializer.initializeDatabase();
            System.out.println("Database initialization " + (initialized ? "successful" : "failed"));
        }
    }
    
    private static void testUserAuthentication() {
        System.out.println("\n=== Testing User Authentication ===");
        
        UserDAO userDAO = new UserDAOImpl();
        
        // Test with test user
        String testEmail = "test@example.com";
        String testPassword = "test123";
        
        // Get user by email
        User user = userDAO.getUserByEmail(testEmail);
        System.out.println("User found by email: " + (user != null));
        
        if (user != null) {
            System.out.println("User ID: " + user.getUserId());
            System.out.println("User Name: " + user.getName());
            System.out.println("User Email: " + user.getEmail());
            System.out.println("User Password: " + user.getPassword());
            
            // Test password verification
            String hashedPassword = PasswordUtil.hashPassword(testPassword);
            System.out.println("Hashed password: " + hashedPassword);
            System.out.println("Stored password: " + user.getPassword());
            
            boolean passwordMatches = hashedPassword.equals(user.getPassword());
            System.out.println("Password matches with hash comparison: " + passwordMatches);
            
            boolean passwordVerified = PasswordUtil.verifyPassword(testPassword, user.getPassword());
            System.out.println("Password verified with PasswordUtil: " + passwordVerified);
        }
        
        // Test with authenticate method
        User authenticatedUser = userDAO.authenticate(testEmail, testPassword);
        System.out.println("User authenticated: " + (authenticatedUser != null));
        
        // Test with admin user
        String adminEmail = "admin@example.com";
        String adminPassword = "admin123";
        
        User adminUser = userDAO.authenticate(adminEmail, adminPassword);
        System.out.println("Admin user authenticated: " + (adminUser != null));
    }
}
