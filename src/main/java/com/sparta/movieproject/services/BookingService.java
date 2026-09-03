package com.sparta.movieproject.services;

import com.sparta.movieproject.dto.BookingDto;
import com.sparta.movieproject.entities.Booking;
import com.sparta.movieproject.entities.Movie;
import com.sparta.movieproject.mappers.BookingMapper;
import com.sparta.movieproject.repository.BookingRepository;
import com.sparta.movieproject.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final MovieRepository movieRepository;
    private final BookingMapper bookingMapper;

    public BookingService(
            BookingRepository bookingRepository,
            MovieRepository movieRepository,
            BookingMapper bookingMapper) {

        this.bookingRepository = bookingRepository;
        this.movieRepository = movieRepository;
        this.bookingMapper = bookingMapper;
    }


    // GET ALL BOOKINGS
    public List<BookingDto> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }


    // GET BOOKING BY ID
    public BookingDto getBookingById(Integer id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Booking not found"));

        return bookingMapper.toDTO(booking);
    }


    // CREATE BOOKING
    public BookingDto saveBooking(BookingDto bookingDto) {

        if (bookingDto == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }

        Movie movie = movieRepository.findById(bookingDto.getMovieId())
                .orElseThrow(() ->
                        new NoSuchElementException("Movie not found"));

        Booking booking = bookingMapper.toEntity(bookingDto);

        booking.setMovie(movie);

        if (booking.getBookingDate() == null) {
            booking.setBookingDate(Instant.now());
        }

        Booking savedBooking = bookingRepository.save(booking);

        return bookingMapper.toDTO(savedBooking);
    }


    // UPDATE BOOKING
    public BookingDto updateBooking(Integer id, BookingDto bookingDto) {

        if (!bookingRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Booking with ID " + id + " does not exist."
            );
        }

        Movie movie = movieRepository.findById(bookingDto.getMovieId())
                .orElseThrow(() ->
                        new NoSuchElementException("Movie not found"));

        Booking booking = bookingMapper.toEntity(bookingDto);

        booking.setId(id);
        booking.setMovie(movie);

        if (booking.getBookingDate() == null) {
            booking.setBookingDate(Instant.now());
        }

        Booking updatedBooking = bookingRepository.save(booking);

        return bookingMapper.toDTO(updatedBooking);
    }


    // DELETE BOOKING
    public boolean deleteBooking(Integer id) {

        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }

        return false;
    }
}