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
import java.util.List;

/**
 * Servlet for displaying movie details and showtimes
 */
@WebServlet(name = "MovieDetailsServlet", urlPatterns = {"/movie/*"})
public class MovieDetailsServlet extends HttpServlet {

    private MovieDAO movieDAO = new MovieDAOImpl();
    private ShowDAO showDAO = new ShowDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Extract movie ID from URL
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            // No movie ID provided, redirect to home page
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        try {
            // Parse movie ID
            int movieId = Integer.parseInt(pathInfo.substring(1));
            
            // Get movie details
            Movie movie = movieDAO.getMovieById(movieId);
            if (movie == null) {
                // Movie not found, redirect to home page
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            
            // Get showtimes for this movie
            List<Show> shows = showDAO.getShowsByMovieId(movieId);
            
            // Set attributes in request
            request.setAttribute("movie", movie);
            request.setAttribute("shows", shows);
            
            // Forward to movie details page
            request.getRequestDispatcher("/WEB-INF/views/movie-details.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // Invalid movie ID, redirect to home page
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
