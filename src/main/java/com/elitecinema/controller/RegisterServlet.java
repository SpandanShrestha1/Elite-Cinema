package com.elitecinema.controller;

import com.elitecinema.dao.UserDAO;
import com.elitecinema.dao.UserDAOImpl;
import com.elitecinema.model.User;
import com.elitecinema.util.DatabaseUtil;
import com.elitecinema.util.ValidationUtil;
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
 * Servlet for user registration
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to registration page
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
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
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // Get form parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate input
        boolean hasError = false;

        if (!ValidationUtil.isValidName(name)) {
            request.setAttribute("nameError", "Name must be at least 2 characters long");
            hasError = true;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("emailError", "Please enter a valid email address");
            hasError = true;
        } else {
            // Check if email already exists
            User existingUser = userDAO.getUserByEmail(email);
            if (existingUser != null) {
                request.setAttribute("emailError", "Email already registered");
                hasError = true;
            }
        }

        if (!ValidationUtil.isValidPassword(password)) {
            request.setAttribute("passwordError", "Password must be at least 6 characters long");
            hasError = true;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("confirmPasswordError", "Passwords do not match");
            hasError = true;
        }

        if (hasError) {
            // Preserve input values
            request.setAttribute("name", name);
            request.setAttribute("email", email);

            // Forward back to registration page with errors
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // SIMPLIFIED REGISTRATION FOR DEBUGGING
        // Store password as plain text for now
        System.out.println("Registering user with email: " + email);
        System.out.println("Password: " + password);

        // Create new user with plain text password
        User user = new User(name, email, password);

        // For debugging, print all user details
        System.out.println("User details before registration:");
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        System.out.println("Is Admin: " + user.isAdmin());

        // Create the user in the database
        int userId = userDAO.createUser(user);
        System.out.println("User registration result - User ID: " + userId);

        if (userId > 0) {
            // Registration successful, set user in session
            user.setUserId(userId);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Redirect to home page
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            // Registration failed
            request.setAttribute("error", "Registration failed. Please try again.");
            request.setAttribute("name", name);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }
}
