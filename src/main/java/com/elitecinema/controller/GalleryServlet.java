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
 * Servlet for gallery page
 */
@WebServlet(name = "GalleryServlet", urlPatterns = {"/gallery"})
public class GalleryServlet extends HttpServlet {

    private MovieDAO movieDAO = new MovieDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Get all movies for gallery
        List<Movie> movies = movieDAO.getAllMovies();
        request.setAttribute("movies", movies);
        
        // Forward to gallery page
        request.getRequestDispatcher("/WEB-INF/views/gallery.jsp").forward(request, response);
    }
}
