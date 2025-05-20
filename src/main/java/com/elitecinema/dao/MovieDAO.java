package com.elitecinema.dao;

import com.elitecinema.model.Movie;
import java.util.List;

/**
 * Interface for Movie data access operations
 */
public interface MovieDAO {

    /**
     * Create a new movie
     * @param movie Movie object to create
     * @return Movie ID if successful, -1 if failed
     */
    int createMovie(Movie movie);

    /**
     * Get movie by ID
     * @param movieId Movie ID
     * @return Movie object if found, null otherwise
     */
    Movie getMovieById(int movieId);

    /**
     * Update movie information
     * @param movie Movie object with updated information
     * @return true if successful, false otherwise
     */
    boolean updateMovie(Movie movie);

    /**
     * Delete movie by ID
     * @param movieId Movie ID
     * @return true if successful, false otherwise
     */
    boolean deleteMovie(int movieId);

    /**
     * Get all movies
     * @return List of all movies
     */
    List<Movie> getAllMovies();

    /**
     * Get movies by genre
     * @param genre Movie genre
     * @return List of movies with the specified genre
     */
    List<Movie> getMoviesByGenre(String genre);

    /**
     * Get movies by status
     * @param status Movie status (e.g., 'now_showing' or 'upcoming')
     * @return List of movies with the specified status
     */
    List<Movie> getMoviesByStatus(String status);

    /**
     * Get movies by genre and status
     * @param genre Movie genre
     * @param status Movie status
     * @return List of movies with the specified genre and status
     */
    List<Movie> getMoviesByGenreAndStatus(String genre, String status);
}
