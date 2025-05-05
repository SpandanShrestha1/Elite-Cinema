package com.elitecinema.controller;

import com.elitecinema.dao.UserDAO;
import com.elitecinema.dao.UserDAOImpl;
import com.elitecinema.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for admin user management
 */
@WebServlet(name = "AdminUserManagementServlet", urlPatterns = {"/admin/users", "/admin/user/*"})
public class AdminUserManagementServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        String servletPath = request.getServletPath();
        
        if (servletPath.equals("/admin/users")) {
            // List all users
            List<User> users = userDAO.getAllUsers();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/WEB-INF/views/admin/admin-users.jsp").forward(request, response);
        } else if (pathInfo != null) {
            if (pathInfo.equals("/delete")) {
                // Delete user
                try {
                    int userId = Integer.parseInt(request.getParameter("id"));
                    userDAO.deleteUser(userId);
                    response.sendRedirect(request.getContextPath() + "/admin/users");
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/admin/users");
                }
            } else if (pathInfo.equals("/toggleAdmin")) {
                // Toggle admin status
                try {
                    int userId = Integer.parseInt(request.getParameter("id"));
                    User user = userDAO.getUserById(userId);
                    
                    if (user != null) {
                        user.setAdmin(!user.isAdmin());
                        userDAO.updateUser(user);
                    }
                    
                    response.sendRedirect(request.getContextPath() + "/admin/users");
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/admin/users");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/users");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/users");
        }
    }
}
