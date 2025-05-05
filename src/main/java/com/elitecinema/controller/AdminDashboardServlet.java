package com.elitecinema.controller;

import com.elitecinema.dao.BookingDAO;
import com.elitecinema.dao.BookingDAOImpl;
import com.elitecinema.dao.MovieDAO;
import com.elitecinema.dao.MovieDAOImpl;
import com.elitecinema.dao.ShowDAO;
import com.elitecinema.dao.ShowDAOImpl;
import com.elitecinema.dao.UserDAO;
import com.elitecinema.dao.UserDAOImpl;
import com.elitecinema.model.Booking;
import com.elitecinema.model.Movie;
import com.elitecinema.model.Show;
import com.elitecinema.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for admin dashboard
 */
@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/admin/dashboard"})
public class AdminDashboardServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAOImpl();
    private MovieDAO movieDAO = new MovieDAOImpl();
    private ShowDAO showDAO = new ShowDAOImpl();
    private BookingDAO bookingDAO = new BookingDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get counts for dashboard
        List<User> users = userDAO.getAllUsers();
        List<Movie> movies = movieDAO.getAllMovies();
        List<Show> shows = showDAO.getAllShows();
        List<Booking> bookings = bookingDAO.getAllBookings();
        
        // Calculate total revenue
        double totalRevenue = bookings.stream()
                .filter(b -> "CONFIRMED".equals(b.getStatus()))
                .mapToDouble(b -> b.getTotalAmount().doubleValue())
                .sum();
        
        // Set attributes in request
        request.setAttribute("userCount", users.size());
        request.setAttribute("movieCount", movies.size());
        request.setAttribute("showCount", shows.size());
        request.setAttribute("bookingCount", bookings.size());
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("recentBookings", bookings.subList(0, Math.min(5, bookings.size())));
        
        // Forward to admin dashboard page
        request.getRequestDispatcher("/WEB-INF/views/admin/admin-dashboard.jsp").forward(request, response);
    }
}
