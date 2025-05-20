package com.elitecinema.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 * Utility class for database setup operations
 */
public class DatabaseSetup {

    /**
     * Execute the SQL script to update the movies table
     */
    public static void updateMoviesTable() {
        Connection conn = null;
        Statement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.createStatement();
            
            // Read SQL script from file
            InputStream is = DatabaseSetup.class.getClassLoader().getResourceAsStream("sql/update_movies_table.sql");
            if (is == null) {
                System.err.println("Could not find update_movies_table.sql");
                return;
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
            
            System.out.println("Movies table updated successfully");
        } catch (Exception e) {
            System.err.println("Error updating movies table: " + e.getMessage());
            e.printStackTrace();
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
     * Main method to run the database setup
     */
    public static void main(String[] args) {
        updateMoviesTable();
    }
}
