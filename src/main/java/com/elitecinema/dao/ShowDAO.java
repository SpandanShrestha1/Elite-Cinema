package com.elitecinema.dao;

import com.elitecinema.model.Show;
import java.sql.Date;
import java.util.List;

/**
 * Interface for Show data access operations
 */
public interface ShowDAO {
    
    /**
     * Create a new show
     * @param show Show object to create
     * @return Show ID if successful, -1 if failed
     */
    int createShow(Show show);
    
    /**
     * Get show by ID
     * @param showId Show ID
     * @return Show object if found, null otherwise
     */
    Show getShowById(int showId);
    
    /**
     * Update show information
     * @param show Show object with updated information
     * @return true if successful, false otherwise
     */
    boolean updateShow(Show show);
    
    /**
     * Delete show by ID
     * @param showId Show ID
     * @return true if successful, false otherwise
     */
    boolean deleteShow(int showId);
    
    /**
     * Get all shows
     * @return List of all shows
     */
    List<Show> getAllShows();
    
    /**
     * Get shows by movie ID
     * @param movieId Movie ID
     * @return List of shows for the specified movie
     */
    List<Show> getShowsByMovieId(int movieId);
    
    /**
     * Get shows by date
     * @param date Show date
     * @return List of shows on the specified date
     */
    List<Show> getShowsByDate(Date date);
    
    /**
     * Update available seats for a show
     * @param showId Show ID
     * @param seatsBooked Number of seats booked
     * @return true if successful, false otherwise
     */
    boolean updateAvailableSeats(int showId, int seatsBooked);
}
