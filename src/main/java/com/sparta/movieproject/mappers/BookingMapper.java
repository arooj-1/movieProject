package com.sparta.movieproject.mappers;

import com.sparta.movieproject.dto.BookingDto;
import com.sparta.movieproject.entities.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "movie.id", target = "movieId")
    BookingDto toDTO(Booking booking);

    @Mapping(target = "movie", ignore = true)
    Booking toEntity(BookingDto bookingDto);
}