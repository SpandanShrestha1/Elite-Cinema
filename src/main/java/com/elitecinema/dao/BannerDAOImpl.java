package com.elitecinema.dao;

import com.elitecinema.model.Banner;
import com.elitecinema.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of BannerDAO interface
 */
public class BannerDAOImpl implements BannerDAO {

    @Override
    public int createBanner(Banner banner) {
        String sql = "INSERT INTO banners (title, description, image_path, active) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, banner.getTitle());
            stmt.setString(2, banner.getDescription());
            stmt.setString(3, banner.getImagePath());
            stmt.setBoolean(4, banner.isActive());

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
    public Banner getBannerById(int bannerId) {
        String sql = "SELECT * FROM banners WHERE banner_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bannerId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return extractBannerFromResultSet(rs);
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
    public boolean updateBanner(Banner banner) {
        String sql = "UPDATE banners SET title = ?, description = ?, image_path = ?, active = ? WHERE banner_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, banner.getTitle());
            stmt.setString(2, banner.getDescription());
            stmt.setString(3, banner.getImagePath());
            stmt.setBoolean(4, banner.isActive());
            stmt.setInt(5, banner.getBannerId());

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
    public boolean deleteBanner(int bannerId) {
        String sql = "DELETE FROM banners WHERE banner_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bannerId);

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
    public List<Banner> getAllBanners() {
        String sql = "SELECT * FROM banners ORDER BY created_at DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Banner> banners = new ArrayList<>();

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                banners.add(extractBannerFromResultSet(rs));
            }
            return banners;
        } catch (SQLException e) {
            e.printStackTrace();
            return banners;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public List<Banner> getActiveBanners() {
        String sql = "SELECT * FROM banners WHERE active = true ORDER BY created_at DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Banner> banners = new ArrayList<>();

        try {
            System.out.println("Executing query for active banners: " + sql);
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                Banner banner = extractBannerFromResultSet(rs);
                banners.add(banner);
                count++;
                System.out.println("Found active banner: ID=" + banner.getBannerId() + ", Title=" + banner.getTitle());
            }
            System.out.println("Total active banners found: " + count);
            return banners;
        } catch (SQLException e) {
            System.out.println("Error retrieving active banners: " + e.getMessage());
            e.printStackTrace();
            return banners;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
    public boolean toggleBannerStatus(int bannerId) {
        String sql = "UPDATE banners SET active = NOT active WHERE banner_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bannerId);

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
     * Extract Banner object from ResultSet
     * @param rs ResultSet containing banner data
     * @return Banner object
     * @throws SQLException if database error occurs
     */
    private Banner extractBannerFromResultSet(ResultSet rs) throws SQLException {
        try {
            Banner banner = new Banner();
            int bannerId = rs.getInt("banner_id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String imagePath = rs.getString("image_path");
            boolean active = rs.getBoolean("active");
            Timestamp createdAt = rs.getTimestamp("created_at");

            banner.setBannerId(bannerId);
            banner.setTitle(title);
            banner.setDescription(description);
            banner.setImagePath(imagePath);
            banner.setActive(active);
            banner.setCreatedAt(createdAt);

            System.out.println("Extracted banner from ResultSet: ID=" + bannerId + ", Title=" + title + ", ImagePath=" + imagePath);

            return banner;
        } catch (SQLException e) {
            System.out.println("Error extracting banner from ResultSet: " + e.getMessage());
            throw e;
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
