package com.elitecinema.dao;

import com.elitecinema.model.Movie;
import com.elitecinema.model.Show;
import com.elitecinema.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ShowDAO interface
 */
public class ShowDAOImpl implements ShowDAO {

    private MovieDAO movieDAO = new MovieDAOImpl();

    @Override
    public int createShow(Show show) {
        String sql = "INSERT INTO shows (movie_id, date, time, total_seats, available_seats, price) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, show.getMovieId());
            stmt.setDate(2, show.getDate());
            stmt.setTime(3, show.getTime());
            stmt.setInt(4, show.getTotalSeats());
            stmt.setInt(5, show.getAvailableSeats());
            stmt.setBigDecimal(6, show.getPrice());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return -1;
            }
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public Show getShowById(int showId) {
        String sql = "SELECT * FROM shows WHERE show_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, showId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Show show = extractShowFromResultSet(rs);
                // Load associated movie
                Movie movie = movieDAO.getMovieById(show.getMovieId());
                show.setMovie(movie);
                return show;
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
    public boolean updateShow(Show show) {
        String sql = "UPDATE shows SET movie_id = ?, date = ?, time = ?, " +
                     "total_seats = ?, available_seats = ?, price = ? WHERE show_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, show.getMovieId());
            stmt.setDate(2, show.getDate());
            stmt.setTime(3, show.getTime());
            stmt.setInt(4, show.getTotalSeats());
            stmt.setInt(5, show.getAvailableSeats());
            stmt.setBigDecimal(6, show.getPrice());
            stmt.setInt(7, show.getShowId());
            
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
    public boolean deleteShow(int showId) {
        String sql = "DELETE FROM shows WHERE show_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, showId);
            
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
    public List<Show> getAllShows() {
        String sql = "SELECT * FROM shows ORDER BY date, time";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Show> shows = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Show show = extractShowFromResultSet(rs);
                // Load associated movie
                Movie movie = movieDAO.getMovieById(show.getMovieId());
                show.setMovie(movie);
                shows.add(show);
            }
            return shows;
        } catch (SQLException e) {
            e.printStackTrace();
            return shows;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public List<Show> getShowsByMovieId(int movieId) {
        String sql = "SELECT * FROM shows WHERE movie_id = ? ORDER BY date, time";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Show> shows = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);
            rs = stmt.executeQuery();
            
            Movie movie = movieDAO.getMovieById(movieId);
            
            while (rs.next()) {
                Show show = extractShowFromResultSet(rs);
                show.setMovie(movie);
                shows.add(show);
            }
            return shows;
        } catch (SQLException e) {
            e.printStackTrace();
            return shows;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public List<Show> getShowsByDate(Date date) {
        String sql = "SELECT * FROM shows WHERE date = ? ORDER BY time";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Show> shows = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setDate(1, date);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Show show = extractShowFromResultSet(rs);
                // Load associated movie
                Movie movie = movieDAO.getMovieById(show.getMovieId());
                show.setMovie(movie);
                shows.add(show);
            }
            return shows;
        } catch (SQLException e) {
            e.printStackTrace();
            return shows;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public boolean updateAvailableSeats(int showId, int seatsBooked) {
        String sql = "UPDATE shows SET available_seats = available_seats - ? WHERE show_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, seatsBooked);
            stmt.setInt(2, showId);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }
    
    /**
     * Extract Show object from ResultSet
     * @param rs ResultSet containing show data
     * @return Show object
     * @throws SQLException if database error occurs
     */
    private Show extractShowFromResultSet(ResultSet rs) throws SQLException {
        Show show = new Show();
        show.setShowId(rs.getInt("show_id"));
        show.setMovieId(rs.getInt("movie_id"));
        show.setDate(rs.getDate("date"));
        show.setTime(rs.getTime("time"));
        show.setTotalSeats(rs.getInt("total_seats"));
        show.setAvailableSeats(rs.getInt("available_seats"));
        show.setPrice(rs.getBigDecimal("price"));
        show.setCreatedAt(rs.getTimestamp("created_at"));
        return show;
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
