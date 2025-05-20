package com.elitecinema.controller;

import com.elitecinema.dao.UserDAO;
import com.elitecinema.dao.UserDAOImpl;
import com.elitecinema.model.User;
import com.elitecinema.util.DatabaseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet for testing database connection and user authentication
 */
@WebServlet(name = "DatabaseTestServlet", urlPatterns = {"/dbtest"})
public class DatabaseTestServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>Database Test</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }");
        out.println("h1, h2, h3 { color: #333; }");
        out.println(".success { color: green; font-weight: bold; }");
        out.println(".error { color: red; font-weight: bold; }");
        out.println(".container { background-color: white; padding: 20px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }");
        out.println("table { border-collapse: collapse; width: 100%; }");
        out.println("table, th, td { border: 1px solid #ddd; }");
        out.println("th, td { padding: 10px; text-align: left; }");
        out.println("th { background-color: #f2f2f2; }");
        out.println("</style>");
        out.println("</head><body>");
        
        out.println("<div class='container'>");
        out.println("<h1>Database Connection Test</h1>");
        
        // Test database connection
        try {
            Connection conn = DatabaseUtil.getConnection();
            out.println("<p class='success'>✓ Database connection successful!</p>");
            
            // Test if users table exists
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'users'");
                if (rs.next()) {
                    out.println("<p class='success'>✓ Users table exists</p>");
                    
                    // Count users
                    rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        out.println("<p>Total users in database: " + count + "</p>");
                    }
                    
                    // List all users
                    rs = stmt.executeQuery("SELECT * FROM users");
                    out.println("<h3>Users in Database:</h3>");
                    out.println("<table>");
                    out.println("<tr><th>ID</th><th>Name</th><th>Email</th><th>Password</th><th>Admin</th></tr>");
                    
                    while (rs.next()) {
                        out.println("<tr>");
                        out.println("<td>" + rs.getInt("user_id") + "</td>");
                        out.println("<td>" + rs.getString("name") + "</td>");
                        out.println("<td>" + rs.getString("email") + "</td>");
                        out.println("<td>" + rs.getString("password") + "</td>");
                        out.println("<td>" + (rs.getBoolean("is_admin") ? "Yes" : "No") + "</td>");
                        out.println("</tr>");
                    }
                    
                    out.println("</table>");
                } else {
                    out.println("<p class='error'>✗ Users table does not exist</p>");
                }
                
                stmt.close();
            } catch (SQLException e) {
                out.println("<p class='error'>✗ Error checking users table: " + e.getMessage() + "</p>");
            }
            
            conn.close();
        } catch (SQLException e) {
            out.println("<p class='error'>✗ Database connection failed: " + e.getMessage() + "</p>");
            out.println("<pre>" + e.toString() + "</pre>");
        }
        
        // Test user authentication
        out.println("<h2>User Authentication Test</h2>");
        
        // Test with test user
        String testEmail = "test@example.com";
        String testPassword = "test123";
        
        out.println("<h3>Test User</h3>");
        out.println("<p>Email: " + testEmail + "</p>");
        out.println("<p>Password: " + testPassword + "</p>");
        
        User testUser = userDAO.authenticate(testEmail, testPassword);
        if (testUser != null) {
            out.println("<p class='success'>✓ Test user authentication successful!</p>");
            out.println("<p>User ID: " + testUser.getUserId() + "</p>");
            out.println("<p>Name: " + testUser.getName() + "</p>");
            out.println("<p>Email: " + testUser.getEmail() + "</p>");
            out.println("<p>Is Admin: " + testUser.isAdmin() + "</p>");
        } else {
            out.println("<p class='error'>✗ Test user authentication failed!</p>");
        }
        
        // Test with admin user
        String adminEmail = "admin@example.com";
        String adminPassword = "admin123";
        
        out.println("<h3>Admin User</h3>");
        out.println("<p>Email: " + adminEmail + "</p>");
        out.println("<p>Password: " + adminPassword + "</p>");
        
        User adminUser = userDAO.authenticate(adminEmail, adminPassword);
        if (adminUser != null) {
            out.println("<p class='success'>✓ Admin user authentication successful!</p>");
            out.println("<p>User ID: " + adminUser.getUserId() + "</p>");
            out.println("<p>Name: " + adminUser.getName() + "</p>");
            out.println("<p>Email: " + adminUser.getEmail() + "</p>");
            out.println("<p>Is Admin: " + adminUser.isAdmin() + "</p>");
        } else {
            out.println("<p class='error'>✗ Admin user authentication failed!</p>");
        }
        
        // Login form for testing
        out.println("<h2>Test Login Form</h2>");
        out.println("<form action='/Elite-Cinema/dbtest' method='post'>");
        out.println("<p>Email: <input type='text' name='email'></p>");
        out.println("<p>Password: <input type='password' name='password'></p>");
        out.println("<p><input type='submit' value='Login'></p>");
        out.println("</form>");
        
        out.println("<p><a href='/Elite-Cinema/login'>Go to Login Page</a></p>");
        out.println("</div>");
        out.println("</body></html>");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>Login Test</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }");
        out.println("h1, h2, h3 { color: #333; }");
        out.println(".success { color: green; font-weight: bold; }");
        out.println(".error { color: red; font-weight: bold; }");
        out.println(".container { background-color: white; padding: 20px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }");
        out.println("</style>");
        out.println("</head><body>");
        
        out.println("<div class='container'>");
        out.println("<h1>Login Test</h1>");
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        out.println("<p>Email: " + email + "</p>");
        out.println("<p>Password: " + password + "</p>");
        
        User user = userDAO.authenticate(email, password);
        if (user != null) {
            out.println("<p class='success'>✓ Login successful!</p>");
            out.println("<p>User ID: " + user.getUserId() + "</p>");
            out.println("<p>Name: " + user.getName() + "</p>");
            out.println("<p>Email: " + user.getEmail() + "</p>");
            out.println("<p>Is Admin: " + user.isAdmin() + "</p>");
        } else {
            out.println("<p class='error'>✗ Login failed!</p>");
        }
        
        out.println("<p><a href='/Elite-Cinema/dbtest'>Back to Test Page</a></p>");
        out.println("</div>");
        out.println("</body></html>");
    }
}
