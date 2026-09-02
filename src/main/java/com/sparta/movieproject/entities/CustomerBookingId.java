package com.sparta.movieproject.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CustomerBookingId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "booking_id", nullable = false)
    private Integer bookingId;

    public CustomerBookingId() {
    }

    public CustomerBookingId(Integer customerId, Integer bookingId) {
        this.customerId = customerId;
        this.bookingId = bookingId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof CustomerBookingId that)) {
            return false;
        }

        return Objects.equals(customerId, that.customerId)
                && Objects.equals(bookingId, that.bookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, bookingId);
    }
}