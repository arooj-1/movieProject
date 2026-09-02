package com.sparta.movieproject.services;

import com.sparta.movieproject.dto.MovieDto;
import com.sparta.movieproject.entities.Movie;
import com.sparta.movieproject.mappers.MovieMapper;
import com.sparta.movieproject.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        if (movieRepository == null || movieMapper == null) {
            throw new IllegalArgumentException(
                    "Repository and Mapper cannot be null");
        }
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    public List<MovieDto> getAllMovies(){
        return movieRepository.findAll().stream().map(movieMapper::toDTO).collect(Collectors.toList());
    }

    public MovieDto getMovieById(int id){
        var result = movieRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Movie not found"));
        return movieMapper.toDTO(result);
    }

    public MovieDto saveMovie (MovieDto movie) {
        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }
        return movieMapper.toDTO(movieRepository.save(movieMapper.toEntity(movie)));
    }

    public boolean deleteMovie(Integer id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public MovieDto updateMovie(MovieDto movie) {
        if (movieRepository.existsById(movie.getId())) {
            Movie entity = movieMapper.toEntity(movie);
            Movie saved = movieRepository.save(entity);
            return movieMapper.toDTO(saved);
        } else {
            throw new IllegalArgumentException("Customer with ID " +movie.getId() + " does not exist.");
        }
    }

}
