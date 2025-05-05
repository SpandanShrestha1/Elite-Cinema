package com.elitecinema.controller;

import com.elitecinema.dao.MovieDAO;
import com.elitecinema.dao.MovieDAOImpl;
import com.elitecinema.model.Movie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet for displaying movie listings
 */
@WebServlet(name = "MovieListServlet", urlPatterns = {"", "/index", "/home"})
public class MovieListServlet extends HttpServlet {

    private MovieDAO movieDAO = new MovieDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get genre filter if provided
        String genre = request.getParameter("genre");
        
        List<Movie> movies;
        if (genre != null && !genre.isEmpty()) {
            // Get movies by genre
            movies = movieDAO.getMoviesByGenre(genre);
            request.setAttribute("selectedGenre", genre);
        } else {
            // Get all movies
            movies = movieDAO.getAllMovies();
        }
        
        // Set movies in request
        request.setAttribute("movies", movies);
        
        // Forward to home page
        request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }
}
