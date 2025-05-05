package com.elitecinema.dao;

import com.elitecinema.model.Booking;
import com.elitecinema.model.Show;
import com.elitecinema.model.User;
import com.elitecinema.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of BookingDAO interface
 */
public class BookingDAOImpl implements BookingDAO {

    private UserDAO userDAO = new UserDAOImpl();
    private ShowDAO showDAO = new ShowDAOImpl();

    @Override
    public int createBooking(Booking booking) {
        String sql = "INSERT INTO bookings (user_id, show_id, seats_booked, seat_numbers, total_amount, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            
            // Start transaction
            conn.setAutoCommit(false);
            
            // Update available seats in the show
            ShowDAO showDAO = new ShowDAOImpl();
            boolean seatsUpdated = showDAO.updateAvailableSeats(booking.getShowId(), booking.getSeatsBooked());
            
            if (!seatsUpdated) {
                conn.rollback();
                return -1;
            }
            
            // Create booking
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getShowId());
            stmt.setInt(3, booking.getSeatsBooked());
            stmt.setString(4, booking.getSeatNumbers());
            stmt.setBigDecimal(5, booking.getTotalAmount());
            stmt.setString(6, booking.getStatus());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                conn.rollback();
                return -1;
            }
            
            rs = stmt.getGeneratedKeys();
            int bookingId = -1;
            if (rs.next()) {
                bookingId = rs.getInt(1);
            }
            
            // Commit transaction
            conn.commit();
            return bookingId;
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public Booking getBookingById(int bookingId) {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookingId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Booking booking = extractBookingFromResultSet(rs);
                // Load associated user and show
                User user = userDAO.getUserById(booking.getUserId());
                Show show = showDAO.getShowById(booking.getShowId());
                booking.setUser(user);
                booking.setShow(show);
                return booking;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public boolean updateBooking(Booking booking) {
        String sql = "UPDATE bookings SET user_id = ?, show_id = ?, seats_booked = ?, " +
                     "seat_numbers = ?, total_amount = ?, status = ? WHERE booking_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getShowId());
            stmt.setInt(3, booking.getSeatsBooked());
            stmt.setString(4, booking.getSeatNumbers());
            stmt.setBigDecimal(5, booking.getTotalAmount());
            stmt.setString(6, booking.getStatus());
            stmt.setInt(7, booking.getBookingId());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            
            // Start transaction
            conn.setAutoCommit(false);
            
            // Get booking details
            String selectSql = "SELECT * FROM bookings WHERE booking_id = ?";
            stmt = conn.prepareStatement(selectSql);
            stmt.setInt(1, bookingId);
            rs = stmt.executeQuery();
            
            if (!rs.next()) {
                conn.rollback();
                return false;
            }
            
            int showId = rs.getInt("show_id");
            int seatsBooked = rs.getInt("seats_booked");
            
            // Close the result set and statement
            rs.close();
            stmt.close();
            
            // Update booking status
            String updateBookingSql = "UPDATE bookings SET status = 'CANCELLED' WHERE booking_id = ?";
            stmt = conn.prepareStatement(updateBookingSql);
            stmt.setInt(1, bookingId);
            
            int bookingUpdated = stmt.executeUpdate();
            if (bookingUpdated == 0) {
                conn.rollback();
                return false;
            }
            
            // Update available seats in the show (add back the cancelled seats)
            String updateShowSql = "UPDATE shows SET available_seats = available_seats + ? WHERE show_id = ?";
            stmt = conn.prepareStatement(updateShowSql);
            stmt.setInt(1, seatsBooked);
            stmt.setInt(2, showId);
            
            int showUpdated = stmt.executeUpdate();
            if (showUpdated == 0) {
                conn.rollback();
                return false;
            }
            
            // Commit transaction
            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public List<Booking> getAllBookings() {
        String sql = "SELECT * FROM bookings ORDER BY booking_date DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Booking> bookings = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Booking booking = extractBookingFromResultSet(rs);
                // Load associated user and show
                User user = userDAO.getUserById(booking.getUserId());
                Show show = showDAO.getShowById(booking.getShowId());
                booking.setUser(user);
                booking.setShow(show);
                bookings.add(booking);
            }
            return bookings;
        } catch (SQLException e) {
            e.printStackTrace();
            return bookings;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public List<Booking> getBookingsByUserId(int userId) {
        String sql = "SELECT * FROM bookings WHERE user_id = ? ORDER BY booking_date DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Booking> bookings = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            
            User user = userDAO.getUserById(userId);
            
            while (rs.next()) {
                Booking booking = extractBookingFromResultSet(rs);
                booking.setUser(user);
                // Load associated show
                Show show = showDAO.getShowById(booking.getShowId());
                booking.setShow(show);
                bookings.add(booking);
            }
            return bookings;
        } catch (SQLException e) {
            e.printStackTrace();
            return bookings;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public List<Booking> getBookingsByShowId(int showId) {
        String sql = "SELECT * FROM bookings WHERE show_id = ? ORDER BY booking_date DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Booking> bookings = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, showId);
            rs = stmt.executeQuery();
            
            Show show = showDAO.getShowById(showId);
            
            while (rs.next()) {
                Booking booking = extractBookingFromResultSet(rs);
                booking.setShow(show);
                // Load associated user
                User user = userDAO.getUserById(booking.getUserId());
                booking.setUser(user);
                bookings.add(booking);
            }
            return bookings;
        } catch (SQLException e) {
            e.printStackTrace();
            return bookings;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    /**
     * Extract Booking object from ResultSet
     * @param rs ResultSet containing booking data
     * @return Booking object
     * @throws SQLException if database error occurs
     */
    private Booking extractBookingFromResultSet(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("booking_id"));
        booking.setUserId(rs.getInt("user_id"));
        booking.setShowId(rs.getInt("show_id"));
        booking.setSeatsBooked(rs.getInt("seats_booked"));
        booking.setSeatNumbers(rs.getString("seat_numbers"));
        booking.setTotalAmount(rs.getBigDecimal("total_amount"));
        booking.setBookingDate(rs.getTimestamp("booking_date"));
        booking.setStatus(rs.getString("status"));
        return booking;
    }
    
    /**
     * Close database resources
     * @param conn Connection object
     * @param stmt PreparedStatement object
     * @param rs ResultSet object
     */
    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
