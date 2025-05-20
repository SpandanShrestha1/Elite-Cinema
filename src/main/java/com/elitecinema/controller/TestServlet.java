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
import java.sql.SQLException;

/**
 * Servlet for testing database connection and user authentication
 */
@WebServlet(name = "TestServlet", urlPatterns = {"/test"})
public class TestServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>Database Test</title></head><body>");
        out.println("<h1>Database Connection Test</h1>");
        
        // Test database connection
        try {
            Connection conn = DatabaseUtil.getConnection();
            out.println("<p style='color:green'>Database connection successful!</p>");
            conn.close();
        } catch (SQLException e) {
            out.println("<p style='color:red'>Database connection failed: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
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
            out.println("<p style='color:green'>Test user authentication successful!</p>");
            out.println("<p>User ID: " + testUser.getUserId() + "</p>");
            out.println("<p>Name: " + testUser.getName() + "</p>");
            out.println("<p>Email: " + testUser.getEmail() + "</p>");
            out.println("<p>Is Admin: " + testUser.isAdmin() + "</p>");
        } else {
            out.println("<p style='color:red'>Test user authentication failed!</p>");
        }
        
        // Test with admin user
        String adminEmail = "admin@example.com";
        String adminPassword = "admin123";
        
        out.println("<h3>Admin User</h3>");
        out.println("<p>Email: " + adminEmail + "</p>");
        out.println("<p>Password: " + adminPassword + "</p>");
        
        User adminUser = userDAO.authenticate(adminEmail, adminPassword);
        if (adminUser != null) {
            out.println("<p style='color:green'>Admin user authentication successful!</p>");
            out.println("<p>User ID: " + adminUser.getUserId() + "</p>");
            out.println("<p>Name: " + adminUser.getName() + "</p>");
            out.println("<p>Email: " + adminUser.getEmail() + "</p>");
            out.println("<p>Is Admin: " + adminUser.isAdmin() + "</p>");
        } else {
            out.println("<p style='color:red'>Admin user authentication failed!</p>");
        }
        
        // Login form for testing
        out.println("<h2>Test Login Form</h2>");
        out.println("<form action='/Elite-Cinema/test' method='post'>");
        out.println("Email: <input type='text' name='email'><br>");
        out.println("Password: <input type='password' name='password'><br>");
        out.println("<input type='submit' value='Login'>");
        out.println("</form>");
        
        out.println("</body></html>");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>Login Test</title></head><body>");
        out.println("<h1>Login Test</h1>");
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        out.println("<p>Email: " + email + "</p>");
        out.println("<p>Password: " + password + "</p>");
        
        User user = userDAO.authenticate(email, password);
        if (user != null) {
            out.println("<p style='color:green'>Login successful!</p>");
            out.println("<p>User ID: " + user.getUserId() + "</p>");
            out.println("<p>Name: " + user.getName() + "</p>");
            out.println("<p>Email: " + user.getEmail() + "</p>");
            out.println("<p>Is Admin: " + user.isAdmin() + "</p>");
        } else {
            out.println("<p style='color:red'>Login failed!</p>");
        }
        
        out.println("<p><a href='/Elite-Cinema/test'>Back to Test Page</a></p>");
        out.println("</body></html>");
    }
}
