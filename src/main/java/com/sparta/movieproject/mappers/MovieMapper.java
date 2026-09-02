package com.sparta.movieproject.mappers;

import com.sparta.movieproject.dto.MovieDto;
import com.sparta.movieproject.entities.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDto toDTO(Movie movie);
    Movie toEntity(MovieDto movieDto);
}
