package com.elitecinema.dao;

import com.elitecinema.model.Movie;
import com.elitecinema.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of MovieDAO interface
 */
public class MovieDAOImpl implements MovieDAO {

    @Override
    public int createMovie(Movie movie) {
        String sql = "INSERT INTO movies (title, genre, description, duration, release_date, image_path) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getGenre());
            stmt.setString(3, movie.getDescription());
            stmt.setInt(4, movie.getDuration());
            stmt.setDate(5, movie.getReleaseDate());
            stmt.setString(6, movie.getImagePath());
            
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
    public Movie getMovieById(int movieId) {
        String sql = "SELECT * FROM movies WHERE movie_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extractMovieFromResultSet(rs);
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
    public boolean updateMovie(Movie movie) {
        String sql = "UPDATE movies SET title = ?, genre = ?, description = ?, " +
                     "duration = ?, release_date = ?, image_path = ? WHERE movie_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getGenre());
            stmt.setString(3, movie.getDescription());
            stmt.setInt(4, movie.getDuration());
            stmt.setDate(5, movie.getReleaseDate());
            stmt.setString(6, movie.getImagePath());
            stmt.setInt(7, movie.getMovieId());
            
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
    public boolean deleteMovie(int movieId) {
        String sql = "DELETE FROM movies WHERE movie_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);
            
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
    public List<Movie> getAllMovies() {
        String sql = "SELECT * FROM movies ORDER BY release_date DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                movies.add(extractMovieFromResultSet(rs));
            }
            return movies;
        } catch (SQLException e) {
            e.printStackTrace();
            return movies;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public List<Movie> getMoviesByGenre(String genre) {
        String sql = "SELECT * FROM movies WHERE genre = ? ORDER BY release_date DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();
        
        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, genre);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                movies.add(extractMovieFromResultSet(rs));
            }
            return movies;
        } catch (SQLException e) {
            e.printStackTrace();
            return movies;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    /**
     * Extract Movie object from ResultSet
     * @param rs ResultSet containing movie data
     * @return Movie object
     * @throws SQLException if database error occurs
     */
    private Movie extractMovieFromResultSet(ResultSet rs) throws SQLException {
        Movie movie = new Movie();
        movie.setMovieId(rs.getInt("movie_id"));
        movie.setTitle(rs.getString("title"));
        movie.setGenre(rs.getString("genre"));
        movie.setDescription(rs.getString("description"));
        movie.setDuration(rs.getInt("duration"));
        movie.setReleaseDate(rs.getDate("release_date"));
        movie.setImagePath(rs.getString("image_path"));
        movie.setCreatedAt(rs.getTimestamp("created_at"));
        movie.setUpdatedAt(rs.getTimestamp("updated_at"));
        return movie;
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
