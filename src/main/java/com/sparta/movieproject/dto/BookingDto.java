package com.sparta.movieproject.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class BookingDto {

    private Integer id;
    private Integer movieId;
    private Instant bookingDate;
    private BigDecimal totalPrice;

    public BookingDto() {
    }

    public BookingDto(Integer id, Integer movieId, Instant bookingDate, BigDecimal totalPrice) {
        this.id = id;
        this.movieId = movieId;
        this.bookingDate = bookingDate;
        this.totalPrice = totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Instant getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Instant bookingDate) {
        this.bookingDate = bookingDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}