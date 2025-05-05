package com.elitecinema.controller;

import com.elitecinema.dao.BookingDAO;
import com.elitecinema.dao.BookingDAOImpl;
import com.elitecinema.model.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for admin bookings management
 */
@WebServlet(name = "AdminBookingsServlet", urlPatterns = {"/admin/bookings", "/admin/booking/*"})
public class AdminBookingsServlet extends HttpServlet {

    private BookingDAO bookingDAO = new BookingDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        String servletPath = request.getServletPath();
        
        if (servletPath.equals("/admin/bookings")) {
            // List all bookings
            List<Booking> bookings = bookingDAO.getAllBookings();
            request.setAttribute("bookings", bookings);
            request.getRequestDispatcher("/WEB-INF/views/admin/admin-bookings.jsp").forward(request, response);
        } else if (pathInfo != null) {
            if (pathInfo.equals("/cancel")) {
                // Cancel booking
                try {
                    int bookingId = Integer.parseInt(request.getParameter("id"));
                    bookingDAO.cancelBooking(bookingId);
                    response.sendRedirect(request.getContextPath() + "/admin/bookings");
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/admin/bookings");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/bookings");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/bookings");
        }
    }
}
