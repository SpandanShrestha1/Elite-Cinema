package com.elitecinema.controller;

import com.elitecinema.dao.MovieDAO;
import com.elitecinema.dao.MovieDAOImpl;
import com.elitecinema.model.Movie;
import com.elitecinema.util.ImageUploadUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * Servlet for admin movie management
 */
@WebServlet(name = "AdminMovieServlet", urlPatterns = {"/admin/movies", "/admin/movie/*"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 5 * 1024 * 1024,   // 5 MB
    maxRequestSize = 10 * 1024 * 1024 // 10 MB
)
public class AdminMovieServlet extends HttpServlet {

    private MovieDAO movieDAO = new MovieDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String servletPath = request.getServletPath();

        if (servletPath.equals("/admin/movies")) {
            // List all movies
            List<Movie> movies = movieDAO.getAllMovies();
            request.setAttribute("movies", movies);
            request.getRequestDispatcher("/WEB-INF/views/admin/admin-movies.jsp").forward(request, response);
        } else if (pathInfo != null) {
            if (pathInfo.equals("/add")) {
                // Show add movie form
                request.getRequestDispatcher("/WEB-INF/views/admin/admin-movie-form.jsp").forward(request, response);
            } else if (pathInfo.equals("/edit")) {
                // Show edit movie form
                try {
                    int movieId = Integer.parseInt(request.getParameter("id"));
                    Movie movie = movieDAO.getMovieById(movieId);

                    if (movie != null) {
                        request.setAttribute("movie", movie);
                        request.getRequestDispatcher("/WEB-INF/views/admin/admin-movie-form.jsp").forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/admin/movies");
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/admin/movies");
                }
            } else if (pathInfo.equals("/delete")) {
                // Delete movie
                try {
                    int movieId = Integer.parseInt(request.getParameter("id"));
                    Movie movie = movieDAO.getMovieById(movieId);

                    if (movie != null) {
                        // Delete movie image if exists
                        if (movie.getImagePath() != null && !movie.getImagePath().isEmpty()) {
                            ImageUploadUtil.deleteImage(movie.getImagePath(), getServletContext().getRealPath("/"));
                        }

                        // Delete movie from database
                        movieDAO.deleteMovie(movieId);
                    }

                    response.sendRedirect(request.getContextPath() + "/admin/movies");
                } catch (NumberFormatException e) {
                    response.sendRedirect(request.getContextPath() + "/admin/movies");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/movies");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/movies");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            // Add new movie
            String title = request.getParameter("title");
            String genre = request.getParameter("genre");
            String description = request.getParameter("description");
            int duration = Integer.parseInt(request.getParameter("duration"));
            Date releaseDate = Date.valueOf(request.getParameter("releaseDate"));
            String status = request.getParameter("status");

            // Handle image upload
            String imagePath = null;
            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                imagePath = ImageUploadUtil.uploadImage(request, "image", getServletContext().getRealPath("/"));
            }

            // Create movie object
            Movie movie = new Movie(title, genre, description, duration, releaseDate, imagePath, status);

            // Save movie to database
            int movieId = movieDAO.createMovie(movie);

            if (movieId > 0) {
                response.sendRedirect(request.getContextPath() + "/admin/movies");
            } else {
                request.setAttribute("error", "Failed to add movie");
                request.setAttribute("movie", movie);
                request.getRequestDispatcher("/WEB-INF/views/admin/admin-movie-form.jsp").forward(request, response);
            }
        } else if ("edit".equals(action)) {
            // Edit existing movie
            int movieId = Integer.parseInt(request.getParameter("movieId"));
            String title = request.getParameter("title");
            String genre = request.getParameter("genre");
            String description = request.getParameter("description");
            int duration = Integer.parseInt(request.getParameter("duration"));
            Date releaseDate = Date.valueOf(request.getParameter("releaseDate"));
            String status = request.getParameter("status");

            // Get existing movie
            Movie movie = movieDAO.getMovieById(movieId);

            if (movie != null) {
                // Handle image upload
                Part filePart = request.getPart("image");
                if (filePart != null && filePart.getSize() > 0) {
                    // Delete old image if exists
                    if (movie.getImagePath() != null && !movie.getImagePath().isEmpty()) {
                        ImageUploadUtil.deleteImage(movie.getImagePath(), getServletContext().getRealPath("/"));
                    }

                    // Upload new image
                    String imagePath = ImageUploadUtil.uploadImage(request, "image", getServletContext().getRealPath("/"));
                    movie.setImagePath(imagePath);
                }

                // Update movie properties
                movie.setTitle(title);
                movie.setGenre(genre);
                movie.setDescription(description);
                movie.setDuration(duration);
                movie.setReleaseDate(releaseDate);
                movie.setStatus(status);

                // Save updated movie to database
                boolean updated = movieDAO.updateMovie(movie);

                if (updated) {
                    response.sendRedirect(request.getContextPath() + "/admin/movies");
                } else {
                    request.setAttribute("error", "Failed to update movie");
                    request.setAttribute("movie", movie);
                    request.getRequestDispatcher("/WEB-INF/views/admin/admin-movie-form.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/movies");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/movies");
        }
    }
}
