package com.elitecinema.controller;

import com.elitecinema.dao.BookingDAO;
import com.elitecinema.dao.BookingDAOImpl;
import com.elitecinema.dao.ShowDAO;
import com.elitecinema.dao.ShowDAOImpl;
import com.elitecinema.model.Booking;
import com.elitecinema.model.Show;
import com.elitecinema.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Servlet for booking tickets
 */
@WebServlet(name = "BookingServlet", urlPatterns = {"/booking/*"})
public class BookingServlet extends HttpServlet {

    private ShowDAO showDAO = new ShowDAOImpl();
    private BookingDAO bookingDAO = new BookingDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // Store the requested URL for redirect after login
            session = request.getSession();
            session.setAttribute("redirectURL", request.getRequestURL().toString());
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        if (pathInfo.equals("/seats")) {
            // Show seat selection page
            try {
                int showId = Integer.parseInt(request.getParameter("showId"));
                Show show = showDAO.getShowById(showId);
                
                if (show != null) {
                    request.setAttribute("show", show);
                    request.getRequestDispatcher("/WEB-INF/views/seat-selection.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/");
            }
        } else if (pathInfo.equals("/payment")) {
            // Show payment page
            try {
                int showId = Integer.parseInt(request.getParameter("showId"));
                String[] selectedSeats = request.getParameterValues("seats");
                
                if (selectedSeats == null || selectedSeats.length == 0) {
                    response.sendRedirect(request.getContextPath() + "/booking/seats?showId=" + showId + "&error=Please select at least one seat");
                    return;
                }
                
                Show show = showDAO.getShowById(showId);
                
                if (show != null) {
                    // Calculate total amount
                    BigDecimal totalAmount = show.getPrice().multiply(new BigDecimal(selectedSeats.length));
                    
                    // Store booking details in session for payment processing
                    session.setAttribute("bookingShowId", showId);
                    session.setAttribute("bookingSeats", selectedSeats);
                    session.setAttribute("bookingTotalAmount", totalAmount);
                    
                    request.setAttribute("show", show);
                    request.setAttribute("selectedSeats", selectedSeats);
                    request.setAttribute("totalAmount", totalAmount);
                    request.getRequestDispatcher("/WEB-INF/views/payment.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/");
            }
        } else if (pathInfo.equals("/confirm")) {
            // Show booking confirmation page
            request.getRequestDispatcher("/WEB-INF/views/booking-confirmation.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        
        String action = request.getParameter("action");
        
        if ("processPayment".equals(action)) {
            // Process payment (mock)
            // In a real application, this would integrate with a payment gateway
            
            // Get booking details from session
            Integer showId = (Integer) session.getAttribute("bookingShowId");
            String[] selectedSeats = (String[]) session.getAttribute("bookingSeats");
            BigDecimal totalAmount = (BigDecimal) session.getAttribute("bookingTotalAmount");
            
            if (showId == null || selectedSeats == null || totalAmount == null) {
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            
            // Create booking
            String seatNumbers = Arrays.stream(selectedSeats).collect(Collectors.joining(","));
            Booking booking = new Booking(user.getUserId(), showId, selectedSeats.length, seatNumbers, totalAmount);
            
            int bookingId = bookingDAO.createBooking(booking);
            
            if (bookingId > 0) {
                // Clear booking details from session
                session.removeAttribute("bookingShowId");
                session.removeAttribute("bookingSeats");
                session.removeAttribute("bookingTotalAmount");
                
                // Set booking confirmation details
                request.setAttribute("bookingId", bookingId);
                request.setAttribute("seatNumbers", seatNumbers);
                request.setAttribute("totalAmount", totalAmount);
                
                // Forward to confirmation page
                response.sendRedirect(request.getContextPath() + "/booking/confirm?bookingId=" + bookingId);
            } else {
                // Booking failed
                response.sendRedirect(request.getContextPath() + "/booking/payment?showId=" + showId + "&error=Booking failed. Please try again.");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
