package com.elitecinema.dao;

import com.elitecinema.model.Banner;
import java.util.List;

/**
 * Interface for Banner data access operations
 */
public interface BannerDAO {
    
    /**
     * Create a new banner
     * @param banner Banner object to create
     * @return Banner ID if successful, -1 if failed
     */
    int createBanner(Banner banner);
    
    /**
     * Get banner by ID
     * @param bannerId Banner ID
     * @return Banner object if found, null otherwise
     */
    Banner getBannerById(int bannerId);
    
    /**
     * Update banner information
     * @param banner Banner object with updated information
     * @return true if successful, false otherwise
     */
    boolean updateBanner(Banner banner);
    
    /**
     * Delete banner by ID
     * @param bannerId Banner ID
     * @return true if successful, false otherwise
     */
    boolean deleteBanner(int bannerId);
    
    /**
     * Get all banners
     * @return List of all banners
     */
    List<Banner> getAllBanners();
    
    /**
     * Get all active banners
     * @return List of all active banners
     */
    List<Banner> getActiveBanners();
    
    /**
     * Toggle banner active status
     * @param bannerId Banner ID
     * @return true if successful, false otherwise
     */
    boolean toggleBannerStatus(int bannerId);
}
