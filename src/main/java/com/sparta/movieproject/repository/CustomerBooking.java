package com.sparta.movieproject.repository;

import com.sparta.movieproject.entities.CustomerBookingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;

public interface CustomerBooking extends JpaRepository<CustomerBooking, CustomerBookingId> {
//    List<CustomerBooking> findByCustomerId(Integer customerId);
}
