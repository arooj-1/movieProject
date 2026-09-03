package com.sparta.movieproject.controllers;

import com.sparta.movieproject.dto.BookingDto;
import com.sparta.movieproject.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // GET ALL BOOKINGS
    @GetMapping
    public ResponseEntity<List<BookingDto>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // GET BOOKING BY ID
    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    // CREATE BOOKING
    @PostMapping
    public ResponseEntity<BookingDto> createBooking(
            @RequestBody BookingDto bookingDto) {

        BookingDto savedBooking = bookingService.saveBooking(bookingDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedBooking);
    }

    // UPDATE BOOKING
    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> updateBooking(
            @PathVariable Integer id,
            @RequestBody BookingDto bookingDto) {

        return ResponseEntity.ok(
                bookingService.updateBooking(id, bookingDto)
        );
    }

    // DELETE BOOKING
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {

        boolean deleted = bookingService.deleteBooking(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}