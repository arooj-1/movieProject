package com.sparta.movieproject.dto;

public class CustomerBookingDto {

    private Integer customerId;
    private Integer bookingId;

    public CustomerBookingDto() {}

    public CustomerBookingDto(Integer customerId, Integer bookingId) {
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
}
