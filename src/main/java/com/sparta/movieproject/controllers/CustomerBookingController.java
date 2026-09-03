package com.sparta.movieproject.controllers;

import com.sparta.movieproject.dto.CustomerBookingDto;
import com.sparta.movieproject.services.CustomerBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer-bookings")
public class CustomerBookingController {

    private final CustomerBookingService customerBookingService;

    public CustomerBookingController(
            CustomerBookingService customerBookingService) {
        this.customerBookingService = customerBookingService;
    }

    @GetMapping()
    public ResponseEntity<List<CustomerBookingDto>>
    getAllCustomerBookings() {

        return ResponseEntity.ok(
                customerBookingService.getAllCustomerBookings());
    }

    @GetMapping("/{customerId}/{bookingId}")
    public ResponseEntity<?> getCustomerBooking(
            @PathVariable Integer customerId,
            @PathVariable Integer bookingId) {

        CustomerBookingDto dto =
                customerBookingService.getCustomerBooking(
                        customerId,
                        bookingId);

        if (dto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCustomerBooking(
            @RequestBody CustomerBookingDto dto) {

        CustomerBookingDto created =
                customerBookingService.createCustomerBooking(dto);

        if (created == null) {
            // Customer/booking doesn't exist,
            // or the relationship already exists.
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Unable to create customer booking");
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CustomerBookingDto>>
    getBookingsForCustomer(
            @PathVariable Integer customerId) {

        return ResponseEntity.ok(
                customerBookingService
                        .getBookingsForCustomer(customerId));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<CustomerBookingDto>>
    getCustomersForBooking(
            @PathVariable Integer bookingId) {

        return ResponseEntity.ok(
                customerBookingService
                        .getCustomersForBooking(bookingId));
    }

    @DeleteMapping("/delete/{customerId}/{bookingId}")
    public ResponseEntity<?> deleteCustomerBooking(
            @PathVariable Integer customerId,
            @PathVariable Integer bookingId) {

        boolean deleted =
                customerBookingService.deleteCustomerBooking(
                        customerId,
                        bookingId);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }
}

