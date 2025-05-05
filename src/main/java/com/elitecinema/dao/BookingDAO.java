package com.elitecinema.dao;

import com.elitecinema.model.Booking;
import java.util.List;

/**
 * Interface for Booking data access operations
 */
public interface BookingDAO {
    
    /**
     * Create a new booking
     * @param booking Booking object to create
     * @return Booking ID if successful, -1 if failed
     */
    int createBooking(Booking booking);
    
    /**
     * Get booking by ID
     * @param bookingId Booking ID
     * @return Booking object if found, null otherwise
     */
    Booking getBookingById(int bookingId);
    
    /**
     * Update booking information
     * @param booking Booking object with updated information
     * @return true if successful, false otherwise
     */
    boolean updateBooking(Booking booking);
    
    /**
     * Cancel booking by ID
     * @param bookingId Booking ID
     * @return true if successful, false otherwise
     */
    boolean cancelBooking(int bookingId);
    
    /**
     * Get all bookings
     * @return List of all bookings
     */
    List<Booking> getAllBookings();
    
    /**
     * Get bookings by user ID
     * @param userId User ID
     * @return List of bookings for the specified user
     */
    List<Booking> getBookingsByUserId(int userId);
    
    /**
     * Get bookings by show ID
     * @param showId Show ID
     * @return List of bookings for the specified show
     */
    List<Booking> getBookingsByShowId(int showId);
}
