package com.elitecinema.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Booking model class
 */
public class Booking {
    private int bookingId;
    private int userId;
    private int showId;
    private int seatsBooked;
    private String seatNumbers;
    private BigDecimal totalAmount;
    private Timestamp bookingDate;
    private String status;
    
    // For joining with User and Show
    private User user;
    private Show show;

    // Default constructor
    public Booking() {
    }

    // Constructor with fields
    public Booking(int bookingId, int userId, int showId, int seatsBooked, String seatNumbers,
                  BigDecimal totalAmount, Timestamp bookingDate, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.showId = showId;
        this.seatsBooked = seatsBooked;
        this.seatNumbers = seatNumbers;
        this.totalAmount = totalAmount;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    // Constructor for creating a new booking
    public Booking(int userId, int showId, int seatsBooked, String seatNumbers, BigDecimal totalAmount) {
        this.userId = userId;
        this.showId = showId;
        this.seatsBooked = seatsBooked;
        this.seatNumbers = seatNumbers;
        this.totalAmount = totalAmount;
        this.status = "CONFIRMED";
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(int seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public String getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(String seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", userId=" + userId +
                ", showId=" + showId +
                ", seatsBooked=" + seatsBooked +
                ", seatNumbers='" + seatNumbers + '\'' +
                ", totalAmount=" + totalAmount +
                ", bookingDate=" + bookingDate +
                ", status='" + status + '\'' +
                '}';
    }
}
