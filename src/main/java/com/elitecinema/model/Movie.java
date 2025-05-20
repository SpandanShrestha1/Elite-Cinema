package com.elitecinema.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Movie model class
 */
public class Movie {
    private int movieId;
    private String title;
    private String genre;
    private String description;
    private int duration;
    private Date releaseDate;
    private String imagePath;
    private String status; // 'now_showing' or 'upcoming'
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default constructor
    public Movie() {
    }

    // Constructor with fields
    public Movie(int movieId, String title, String genre, String description, int duration,
                 Date releaseDate, String imagePath, String status, Timestamp createdAt, Timestamp updatedAt) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.imagePath = imagePath;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Constructor for creating a new movie
    public Movie(String title, String genre, String description, int duration,
                 Date releaseDate, String imagePath, String status) {
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.imagePath = imagePath;
        this.status = status;
    }

    // Getters and Setters
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", releaseDate=" + releaseDate +
                ", imagePath='" + imagePath + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
