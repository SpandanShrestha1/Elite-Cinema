package com.elitecinema.filter;

import com.elitecinema.model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Filter to protect user-only and admin-only pages
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/user/*", "/admin/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        String requestURI = httpRequest.getRequestURI();
        
        // Check if user is logged in
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        boolean isAdminPage = requestURI.contains("/admin/");
        
        if (!isLoggedIn) {
            // User is not logged in, redirect to login page
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=Please login to access this page");
            return;
        } else {
            // User is logged in, check if admin access is required
            if (isAdminPage) {
                User user = (User) session.getAttribute("user");
                if (!user.isAdmin()) {
                    // User is not an admin, redirect to access denied page
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/access-denied.jsp");
                    return;
                }
            }
        }
        
        // User is authenticated and authorized, continue with the request
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
