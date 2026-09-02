package com.sparta.movieproject.controllers;
import com.sparta.movieproject.dto.MovieDto;
import com.sparta.movieproject.services.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    //    @GetMapping(value = "/")
    @GetMapping()
    public ResponseEntity<List<MovieDto>> getAllCustomers() {
        var customers = service.getAllMovies();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Integer id) {
        MovieDto movie = service.getMovieById(id);
        if (movie != null) {
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<MovieDto> addMovie(@RequestBody MovieDto movie) {
        MovieDto savedMovie = service.saveMovie(movie);
        return ResponseEntity.status(201).body(savedMovie);
    }

    public ResponseEntity<MovieDto> updateMovie(@PathVariable Integer id, @RequestBody MovieDto movie) {
        movie.setId(id);
        try {
            MovieDto updatedMovie = service.updateMovie(movie);
            return ResponseEntity.ok(updatedMovie);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMvoie(@PathVariable Integer id) {
        boolean deleted = service.deleteMovie(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
