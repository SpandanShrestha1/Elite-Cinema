package com.elitecinema.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class for database connection management
 */
public class DatabaseUtil {
    // Database connection settings
    // These can be modified to match your MySQL configuration
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "elitecinema";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "password"; // Empty for default MySQL installation

    // Connection URLs with common parameters for compatibility
    private static final String JDBC_URL = String.format(
        "jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8",
        DB_HOST, DB_PORT, DB_NAME);
    private static final String ROOT_JDBC_URL = String.format(
        "jdbc:mysql://%s:%s?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8",
        DB_HOST, DB_PORT);

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC driver loaded successfully");

            // Test database connection on startup
            try {
                Connection testConn = getConnection();
                testConn.close();
                System.out.println("Initial database connection test successful");
            } catch (SQLException e) {
                System.err.println("WARNING: Initial database connection test failed: " + e.getMessage());
                System.err.println("Please check your database settings and ensure MySQL is running");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load MySQL JDBC driver");
        }
    }

    /**
     * Get a database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        // Print connection details for debugging
        System.out.println("Attempting to connect to database with:");
        System.out.println("URL: " + JDBC_URL);
        System.out.println("User: " + JDBC_USER);
        System.out.println("Password length: " + (JDBC_PASSWORD != null ? JDBC_PASSWORD.length() : 0));

        try {
            // First try direct connection with createDatabaseIfNotExist=true parameter
            try {
                Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                System.out.println("Database connection successful");

                // Initialize database tables
                initializeTables(conn);

                return conn;
            } catch (SQLException e) {
                System.err.println("Direct connection failed: " + e.getMessage());
                // Continue to fallback method
            }

            // Fallback: Try to create the database manually
            System.out.println("Trying fallback method to create database...");
            Connection rootConn = null;
            try {
                rootConn = DriverManager.getConnection(ROOT_JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                Statement stmt = rootConn.createStatement();
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
                stmt.close();
                System.out.println("Database created successfully");

                // Now try to connect to the database again
                Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                System.out.println("Database connection successful after manual creation");

                // Initialize database tables
                initializeTables(conn);

                return conn;
            } catch (SQLException innerEx) {
                System.err.println("Fallback method failed: " + innerEx.getMessage());
                throw innerEx;
            } finally {
                if (rootConn != null) {
                    try {
                        rootConn.close();
                    } catch (SQLException closeEx) {
                        // Ignore
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("All database connection attempts failed: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Initialize database tables and test users
     * @param conn Database connection
     */
    private static void initializeTables(Connection conn) {
        try {
            Statement stmt = conn.createStatement();

            // Create users table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "user_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "email VARCHAR(100) NOT NULL UNIQUE, " +
                "password VARCHAR(255) NOT NULL, " +
                "is_admin BOOLEAN DEFAULT FALSE, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.executeUpdate(createTableSQL);
            System.out.println("Users table created/verified successfully");

            // Check if test users exist
            try {
                String checkUserSQL = "SELECT COUNT(*) FROM users WHERE email = 'test@example.com'";
                ResultSet rs = stmt.executeQuery(checkUserSQL);
                rs.next();
                int count = rs.getInt(1);

                if (count == 0) {
                    // Create a test user
                    String insertUserSQL = "INSERT INTO users (name, email, password, is_admin) " +
                        "VALUES ('Test User', 'test@example.com', 'test123', FALSE)";
                    stmt.executeUpdate(insertUserSQL);
                    System.out.println("Created test user: test@example.com / test123");

                    // Create an admin user
                    String insertAdminSQL = "INSERT INTO users (name, email, password, is_admin) " +
                        "VALUES ('Admin User', 'admin@example.com', 'admin123', TRUE)";
                    stmt.executeUpdate(insertAdminSQL);
                    System.out.println("Created admin user: admin@example.com / admin123");
                } else {
                    System.out.println("Test users already exist");
                }
            } catch (SQLException e) {
                System.err.println("Error checking/creating test users: " + e.getMessage());
            }

            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error initializing database tables: " + e.getMessage());
        }
    }

    /**
     * Close a database connection safely
     * @param connection Connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get a connection to the MySQL server without specifying a database
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getRootConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(ROOT_JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            System.out.println("Root database connection successful");
            return conn;
        } catch (SQLException e) {
            System.err.println("Root database connection failed: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Check if the database exists
     * @return true if the database exists, false otherwise
     */
    public static boolean checkDatabaseExists() {
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = getRootConnection();
            rs = conn.getMetaData().getCatalogs();
            while (rs.next()) {
                String dbName = rs.getString(1);
                if ("elitecinema".equalsIgnoreCase(dbName)) {
                    System.out.println("Database 'elitecinema' exists");
                    return true;
                }
            }
            System.out.println("Database 'elitecinema' does not exist");
            return false;
        } catch (SQLException e) {
            System.err.println("Error checking if database exists: " + e.getMessage());
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Check if the users table exists
     * @return true if the table exists, false otherwise
     */
    public static boolean checkUsersTableExists() {
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            DatabaseMetaData meta = conn.getMetaData();
            rs = meta.getTables(null, null, "users", new String[] {"TABLE"});
            boolean exists = rs.next();
            System.out.println("Users table exists: " + exists);
            return exists;
        } catch (SQLException e) {
            System.err.println("Error checking if users table exists: " + e.getMessage());
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
