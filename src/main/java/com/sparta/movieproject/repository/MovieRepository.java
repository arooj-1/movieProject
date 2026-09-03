package com.sparta.movieproject.repository;

import com.sparta.movieproject.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
