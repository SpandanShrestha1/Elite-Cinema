package com.elitecinema.model;

import java.sql.Timestamp;

/**
 * Model class for Banner
 */
public class Banner {
    private int bannerId;
    private String title;
    private String description;
    private String imagePath;
    private boolean active;
    private Timestamp createdAt;

    /**
     * Default constructor
     */
    public Banner() {
    }

    /**
     * Constructor with fields
     * @param title Banner title
     * @param description Banner description
     * @param imagePath Path to banner image
     * @param active Whether banner is active
     */
    public Banner(String title, String description, String imagePath, boolean active) {
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.active = active;
    }

    /**
     * Constructor with ID
     * @param bannerId Banner ID
     * @param title Banner title
     * @param description Banner description
     * @param imagePath Path to banner image
     * @param active Whether banner is active
     */
    public Banner(int bannerId, String title, String description, String imagePath, boolean active) {
        this.bannerId = bannerId;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.active = active;
    }

    /**
     * Get banner ID
     * @return Banner ID
     */
    public int getBannerId() {
        return bannerId;
    }

    /**
     * Set banner ID
     * @param bannerId Banner ID
     */
    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }

    /**
     * Get banner title
     * @return Banner title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set banner title
     * @param title Banner title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get banner description
     * @return Banner description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set banner description
     * @param description Banner description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get path to banner image
     * @return Path to banner image
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Set path to banner image
     * @param imagePath Path to banner image
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Check if banner is active
     * @return true if banner is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set banner active status
     * @param active Whether banner is active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Get banner creation timestamp
     * @return Banner creation timestamp
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Set banner creation timestamp
     * @param createdAt Banner creation timestamp
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
