package com.elitecinema.controller;

import com.elitecinema.dao.UserDAO;
import com.elitecinema.dao.UserDAOImpl;
import com.elitecinema.model.User;
import com.elitecinema.util.DatabaseUtil;
import com.elitecinema.util.PasswordUtil;

import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet for user login
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is already logged in
        HttpSession session = request.getSession(false);

        // Forward to login page
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Try to establish database connection
        try {
            // Just getting a connection will initialize the database if needed
            Connection conn = DatabaseUtil.getConnection();
            conn.close();
            System.out.println("Database connection and initialization successful");
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            request.setAttribute("error", "Database connection failed. Please try again later or contact the administrator.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        // Get form parameters
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // For debugging purposes
        System.out.println("Login attempt for email: " + email);
        System.out.println("Password provided: " + password);

        // SIMPLIFIED LOGIN FOR DEBUGGING
        // Just use direct authentication with plain text password
        User user = userDAO.authenticate(email, password);
        System.out.println("Authentication result: " + (user != null));

        if (user != null) {
            // Login successful, set user in session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Redirect based on user role
            if (user.isAdmin()) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                // Redirect to the page user was trying to access, or home page
                String redirectURL = (String) session.getAttribute("redirectURL");
                if (redirectURL != null) {
                    session.removeAttribute("redirectURL");
                    response.sendRedirect(redirectURL);
                } else {
                    response.sendRedirect(request.getContextPath() + "/");
                }
            }
        } else {
            // Login failed
            request.setAttribute("error", "Invalid email or password");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
