package com.sparta.movieproject.repository;

import com.sparta.movieproject.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(exported = false)
public interface BookingRepository extends JpaRepository <Booking, Integer> {
    List<Booking> findByMovieId(Integer movieId);
}
