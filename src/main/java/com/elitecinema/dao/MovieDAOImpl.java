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
        String sql = "INSERT INTO movies (title, genre, description, duration, release_date, image_path, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            stmt.setString(7, movie.getStatus());

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
                     "duration = ?, release_date = ?, image_path = ?, status = ? WHERE movie_id = ?";
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
            stmt.setString(7, movie.getStatus());
            stmt.setInt(8, movie.getMovieId());

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
        movie.setStatus(rs.getString("status"));
        movie.setCreatedAt(rs.getTimestamp("created_at"));
        movie.setUpdatedAt(rs.getTimestamp("updated_at"));
        return movie;
    }

    @Override
    public List<Movie> getMoviesByStatus(String status) {
        String sql = "SELECT * FROM movies WHERE status = ? ORDER BY release_date DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();

        try {
            System.out.println("Executing query for movies with status '" + status + "': " + sql);
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            rs = stmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                Movie movie = extractMovieFromResultSet(rs);
                movies.add(movie);
                count++;
                System.out.println("Found movie with status '" + status + "': ID=" + movie.getMovieId() + ", Title=" + movie.getTitle());
            }
            System.out.println("Total movies found with status '" + status + "': " + count);

            if (count == 0) {
                System.out.println("No movies found with status '" + status + "'. Checking if status column exists...");
                // Check if status column exists
                try {
                    String checkSql = "SELECT status FROM movies LIMIT 1";
                    PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                    ResultSet checkRs = checkStmt.executeQuery();
                    if (checkRs.next()) {
                        String foundStatus = checkRs.getString("status");
                        System.out.println("Status column exists. Sample value: " + foundStatus);
                    } else {
                        System.out.println("Status column exists but no rows found.");
                    }
                    checkRs.close();
                    checkStmt.close();
                } catch (SQLException e) {
                    System.out.println("Error checking status column: " + e.getMessage());
                }
            }

            return movies;
        } catch (SQLException e) {
            System.out.println("Error retrieving movies with status '" + status + "': " + e.getMessage());
            e.printStackTrace();
            return movies;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public List<Movie> getMoviesByGenreAndStatus(String genre, String status) {
        String sql = "SELECT * FROM movies WHERE genre = ? AND status = ? ORDER BY release_date DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Movie> movies = new ArrayList<>();

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, genre);
            stmt.setString(2, status);
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
