package com.elitecinema.controller;

import com.elitecinema.dao.MovieDAO;
import com.elitecinema.dao.MovieDAOImpl;
import com.elitecinema.dao.ShowDAO;
import com.elitecinema.dao.ShowDAOImpl;
import com.elitecinema.model.Movie;
import com.elitecinema.model.Show;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Servlet for admin show management
 */
@WebServlet(name = "AdminShowServlet", urlPatterns = {"/admin/shows", "/admin/show/*"})
public class AdminShowServlet extends HttpServlet {

    private ShowDAO showDAO = new ShowDAOImpl();
    private MovieDAO movieDAO = new MovieDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        String servletPath = request.getServletPath();
        
        if (servletPath.equals("/admin/shows")) {
            // List all shows
            List<Show> shows = showDAO.getAllShows();
            request.setAttribute("shows", shows);
            request.getRequestDispatcher("/WEB-INF/views/admin/admin-shows.jsp").forward(request, response);
        } else if (pathInfo != null) {
            if (pathInfo.equals("/add")) {
                // Show add show form
                List<Movie> movies = movieDAO.getAllMovies();
                request.setAttribute("movies", movies);
                request.getRequestDispatcher("/WEB-INF/views/admin/admin-show-form.jsp").forward(request, response);
            } else if (pathInfo.equals("/edit")) {
                // Show edit show form
                try {
                    int showId = Integer.parseInt(request.getParameter("id"));
                    Show show = showDAO.getShowById(showId);
                    
                    if (show != null) {
                        List<Movie> movies = movieDAO.getAllMovies();
                        request.setAttribute("movies", movies);
                        request.setAttribute("show", show);
                        request.getRequestDispatcher("/WEB-INF/views/admin/admin-show-form.jsp").forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/admin/shows");
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/admin/shows");
                }
            } else if (pathInfo.equals("/delete")) {
                // Delete show
                try {
                    int showId = Integer.parseInt(request.getParameter("id"));
                    showDAO.deleteShow(showId);
                    response.sendRedirect(request.getContextPath() + "/admin/shows");
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/admin/shows");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/shows");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/shows");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            // Add new show
            int movieId = Integer.parseInt(request.getParameter("movieId"));
            Date date = Date.valueOf(request.getParameter("date"));
            Time time = Time.valueOf(request.getParameter("time") + ":00");
            int totalSeats = Integer.parseInt(request.getParameter("totalSeats"));
            BigDecimal price = new BigDecimal(request.getParameter("price"));
            
            // Create show object
            Show show = new Show(movieId, date, time, totalSeats, totalSeats, price);
            
            // Save show to database
            int showId = showDAO.createShow(show);
            
            if (showId > 0) {
                response.sendRedirect(request.getContextPath() + "/admin/shows");
            } else {
                List<Movie> movies = movieDAO.getAllMovies();
                request.setAttribute("movies", movies);
                request.setAttribute("error", "Failed to add show");
                request.setAttribute("show", show);
                request.getRequestDispatcher("/WEB-INF/views/admin/admin-show-form.jsp").forward(request, response);
            }
        } else if ("edit".equals(action)) {
            // Edit existing show
            int showId = Integer.parseInt(request.getParameter("showId"));
            int movieId = Integer.parseInt(request.getParameter("movieId"));
            Date date = Date.valueOf(request.getParameter("date"));
            Time time = Time.valueOf(request.getParameter("time") + ":00");
            int totalSeats = Integer.parseInt(request.getParameter("totalSeats"));
            int availableSeats = Integer.parseInt(request.getParameter("availableSeats"));
            BigDecimal price = new BigDecimal(request.getParameter("price"));
            
            // Get existing show
            Show show = showDAO.getShowById(showId);
            
            if (show != null) {
                // Update show properties
                show.setMovieId(movieId);
                show.setDate(date);
                show.setTime(time);
                show.setTotalSeats(totalSeats);
                show.setAvailableSeats(availableSeats);
                show.setPrice(price);
                
                // Save updated show to database
                boolean updated = showDAO.updateShow(show);
                
                if (updated) {
                    response.sendRedirect(request.getContextPath() + "/admin/shows");
                } else {
                    List<Movie> movies = movieDAO.getAllMovies();
                    request.setAttribute("movies", movies);
                    request.setAttribute("error", "Failed to update show");
                    request.setAttribute("show", show);
                    request.getRequestDispatcher("/WEB-INF/views/admin/admin-show-form.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/shows");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/shows");
        }
    }
}
