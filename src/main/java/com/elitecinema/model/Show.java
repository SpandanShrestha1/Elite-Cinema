package com.elitecinema.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.math.BigDecimal;

/**
 * Show model class
 */
public class Show {
    private int showId;
    private int movieId;
    private Date date;
    private Time time;
    private int totalSeats;
    private int availableSeats;
    private BigDecimal price;
    private Timestamp createdAt;
    
    // For joining with Movie
    private Movie movie;

    // Default constructor
    public Show() {
    }

    // Constructor with fields
    public Show(int showId, int movieId, Date date, Time time, int totalSeats, 
                int availableSeats, BigDecimal price, Timestamp createdAt) {
        this.showId = showId;
        this.movieId = movieId;
        this.date = date;
        this.time = time;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.price = price;
        this.createdAt = createdAt;
    }

    // Constructor for creating a new show
    public Show(int movieId, Date date, Time time, int totalSeats, 
                int availableSeats, BigDecimal price) {
        this.movieId = movieId;
        this.date = date;
        this.time = time;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.price = price;
    }

    // Getters and Setters
    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "Show{" +
                "showId=" + showId +
                ", movieId=" + movieId +
                ", date=" + date +
                ", time=" + time +
                ", totalSeats=" + totalSeats +
                ", availableSeats=" + availableSeats +
                ", price=" + price +
                '}';
    }
}
