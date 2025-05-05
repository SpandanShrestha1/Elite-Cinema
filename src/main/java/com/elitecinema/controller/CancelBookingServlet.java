package com.elitecinema.controller;

import com.elitecinema.dao.BookingDAO;
import com.elitecinema.dao.BookingDAOImpl;
import com.elitecinema.model.Booking;
import com.elitecinema.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet for cancelling bookings
 */
@WebServlet(name = "CancelBookingServlet", urlPatterns = {"/user/booking/cancel"})
public class CancelBookingServlet extends HttpServlet {

    private BookingDAO bookingDAO = new BookingDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        try {
            // Get booking ID from request
            int bookingId = Integer.parseInt(request.getParameter("id"));
            
            // Get booking details
            Booking booking = bookingDAO.getBookingById(bookingId);
            
            // Check if booking exists and belongs to the user
            if (booking != null && booking.getUserId() == user.getUserId()) {
                // Cancel booking
                boolean cancelled = bookingDAO.cancelBooking(bookingId);
                
                if (cancelled) {
                    // Redirect to bookings page with success message
                    response.sendRedirect(request.getContextPath() + "/user/bookings?message=Booking cancelled successfully");
                } else {
                    // Redirect to bookings page with error message
                    response.sendRedirect(request.getContextPath() + "/user/bookings?error=Failed to cancel booking");
                }
            } else {
                // Booking not found or doesn't belong to user
                response.sendRedirect(request.getContextPath() + "/user/bookings?error=Invalid booking");
            }
        } catch (NumberFormatException e) {
            // Invalid booking ID
            response.sendRedirect(request.getContextPath() + "/user/bookings?error=Invalid booking ID");
        }
    }
}
