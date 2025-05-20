package com.elitecinema.controller;

import com.elitecinema.dao.BannerDAO;
import com.elitecinema.dao.BannerDAOImpl;
import com.elitecinema.dao.MovieDAO;
import com.elitecinema.dao.MovieDAOImpl;
import com.elitecinema.model.Banner;
import com.elitecinema.model.Movie;
import com.elitecinema.util.DatabaseSetup;

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
    private BannerDAO bannerDAO = new BannerDAOImpl();

    @Override
    public void init() throws ServletException {
        super.init();
        // Run database setup to ensure movies table has status column
        try {
            System.out.println("Running database setup for movies table...");
            DatabaseSetup.updateMoviesTable();
        } catch (Exception e) {
            System.err.println("Error during database setup: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get genre filter if provided
        String genre = request.getParameter("genre");

        // Get now showing movies
        List<Movie> nowShowingMovies;
        if (genre != null && !genre.isEmpty()) {
            // Get now showing movies by genre
            nowShowingMovies = movieDAO.getMoviesByGenreAndStatus(genre, "now_showing");
            request.setAttribute("selectedGenre", genre);
        } else {
            // Get all now showing movies
            nowShowingMovies = movieDAO.getMoviesByStatus("now_showing");
        }

        // Get upcoming movies
        List<Movie> upcomingMovies = movieDAO.getMoviesByStatus("upcoming");
        System.out.println("Retrieved " + upcomingMovies.size() + " upcoming movies");

        // Debug upcoming movies information
        for (Movie movie : upcomingMovies) {
            System.out.println("Upcoming Movie ID: " + movie.getMovieId());
            System.out.println("Upcoming Movie Title: " + movie.getTitle());
            System.out.println("Upcoming Movie Status: " + movie.getStatus());
            System.out.println("Upcoming Movie Release Date: " + movie.getReleaseDate());
        }

        // Get active banners for hero slider
        List<Banner> banners = bannerDAO.getActiveBanners();
        System.out.println("Retrieved " + banners.size() + " active banners");

        // Debug banner information
        for (Banner banner : banners) {
            System.out.println("Banner ID: " + banner.getBannerId());
            System.out.println("Banner Title: " + banner.getTitle());
            System.out.println("Banner Image Path: " + banner.getImagePath());
            System.out.println("Banner Active: " + banner.isActive());
        }

        // Set attributes in request
        request.setAttribute("nowShowingMovies", nowShowingMovies);
        request.setAttribute("upcomingMovies", upcomingMovies);
        request.setAttribute("banners", banners);

        // Forward to home page
        request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }
}
