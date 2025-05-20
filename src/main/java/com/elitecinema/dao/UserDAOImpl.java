package com.elitecinema.dao;

import com.elitecinema.model.User;
import com.elitecinema.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of UserDAO interface
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public int createUser(User user) {
        String sql = "INSERT INTO users (name, email, password, is_admin) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            System.out.println("Attempting to create user in database:");
            System.out.println("Name: " + user.getName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Password length: " + (user.getPassword() != null ? user.getPassword().length() : 0));
            System.out.println("Is Admin: " + user.isAdmin());

            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setBoolean(4, user.isAdmin());

            System.out.println("Executing SQL: " + sql);
            int affectedRows = stmt.executeUpdate();
            System.out.println("Affected rows: " + affectedRows);

            if (affectedRows == 0) {
                System.out.println("No rows affected, user creation failed");
                return -1;
            }

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);
                System.out.println("User created successfully with ID: " + userId);
                return userId;
            } else {
                System.out.println("Failed to get generated keys");
                return -1;
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception during user creation: " + e.getMessage());
            e.printStackTrace();
            return -1;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            System.out.println("Looking up user by email: " + email);
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            System.out.println("Executing SQL: " + sql + " with email: " + email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                User user = extractUserFromResultSet(rs);
                System.out.println("User found with ID: " + user.getUserId());
                System.out.println("User name: " + user.getName());
                System.out.println("User email: " + user.getEmail());
                System.out.println("User password length: " + (user.getPassword() != null ? user.getPassword().length() : 0));
                return user;
            }
            System.out.println("No user found with email: " + email);
            return null;
        } catch (SQLException e) {
            System.err.println("SQL Exception during user lookup by email: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, password = ?, is_admin = ? WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setBoolean(4, user.isAdmin());
            stmt.setInt(5, user.getUserId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    @Override
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return users;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public User authenticate(String email, String password) {
        System.out.println("Authenticating user with email: " + email);

        // SIMPLIFIED AUTHENTICATION FOR DEBUGGING
        // Try direct SQL query for authentication with plain text password
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Get database connection - this will initialize the database if needed
            conn = DatabaseUtil.getConnection();

            // First try: exact match on email and password
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            System.out.println("Executing SQL: " + sql);
            System.out.println("With parameters: email=" + email + ", password=" + password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                User user = extractUserFromResultSet(rs);
                System.out.println("User authenticated successfully with exact match");
                return user;
            }

            // Second try: get user by email and compare password case-insensitive
            closeResources(null, stmt, rs);
            String emailOnlySQL = "SELECT * FROM users WHERE email = ?";
            stmt = conn.prepareStatement(emailOnlySQL);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                User user = extractUserFromResultSet(rs);
                String storedPassword = user.getPassword();

                // Try case-insensitive comparison
                if (storedPassword != null && storedPassword.equalsIgnoreCase(password)) {
                    System.out.println("User authenticated successfully with case-insensitive match");
                    return user;
                }

                System.out.println("User found but password doesn't match");
                System.out.println("Stored password: " + storedPassword);
                System.out.println("Provided password: " + password);
            }

            System.out.println("No matching user found");
            return null;
        } catch (SQLException e) {
            System.err.println("SQL Exception during authentication: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    /**
     * Extract User object from ResultSet
     * @param rs ResultSet containing user data
     * @return User object
     * @throws SQLException if database error occurs
     */
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setAdmin(rs.getBoolean("is_admin"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }

    /**
     * Close database resources
     * @param conn Connection object
     * @param stmt PreparedStatement object
     * @param rs ResultSet object
     */
    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
