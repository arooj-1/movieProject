package com.sparta.movieproject.repository;

import com.sparta.movieproject.entities.CustomerBooking;
import com.sparta.movieproject.entities.CustomerBookingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RepositoryRestResource(exported = false)
public interface CustomerBookingRepository
        extends JpaRepository<CustomerBooking, CustomerBookingId> {

    List<CustomerBooking> findByCustomerId(Integer customerId);

    List<CustomerBooking> findByBookingId(Integer bookingId);
}