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
import java.util.List;

/**
 * Servlet for viewing user bookings
 */
@WebServlet(name = "ViewBookingsServlet", urlPatterns = {"/user/bookings"})
public class ViewBookingsServlet extends HttpServlet {

    private BookingDAO bookingDAO = new BookingDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get user from session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        
        // Get user's bookings
        List<Booking> bookings = bookingDAO.getBookingsByUserId(user.getUserId());
        
        // Set bookings in request
        request.setAttribute("bookings", bookings);
        
        // Forward to user bookings page
        request.getRequestDispatcher("/WEB-INF/views/user-bookings.jsp").forward(request, response);
    }
}
