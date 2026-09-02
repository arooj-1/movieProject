package com.sparta.movieproject.repository;

import com.sparta.movieproject.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository <Booking, Integer> {
    List<Booking> findByMovieId(Integer movieId);
}
