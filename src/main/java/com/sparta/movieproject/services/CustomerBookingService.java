package com.sparta.movieproject.services;

import com.sparta.movieproject.dto.CustomerBookingDto;
import com.sparta.movieproject.mappers.CustomerBookingMapper;
import com.sparta.movieproject.repository.BookingRepository;
import com.sparta.movieproject.repository.CustomerBookingRepository;
import com.sparta.movieproject.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sparta.movieproject.entities.Booking;
import com.sparta.movieproject.entities.Customer;
import com.sparta.movieproject.entities.CustomerBooking;
import com.sparta.movieproject.entities.CustomerBookingId;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerBookingService {

    private final CustomerBookingRepository customerBookingRepository;
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final CustomerBookingMapper customerBookingMapper;

    @Autowired
    public CustomerBookingService(
            CustomerBookingRepository customerBookingRepository,
            CustomerRepository customerRepository,
            BookingRepository bookingRepository,
            CustomerBookingMapper customerBookingMapper) {

        this.customerBookingRepository = customerBookingRepository;
        this.customerRepository = customerRepository;
        this.bookingRepository = bookingRepository;
        this.customerBookingMapper = customerBookingMapper;
    }

    // Get all customer-booking links
    public List<CustomerBookingDto> getAllCustomerBookings() {
        return customerBookingRepository.findAll()
                .stream()
                .map(customerBookingMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get one link using the composite key
    public CustomerBookingDto getCustomerBooking(
            Integer customerId,
            Integer bookingId) {

        CustomerBookingId id =
                new CustomerBookingId(customerId, bookingId);

        CustomerBooking customerBooking =
                customerBookingRepository.findById(id).orElse(null);

        if (customerBooking == null) {
            return null;
        }

        return customerBookingMapper.toDto(customerBooking);
    }

    // Create a link between a customer and a booking
    public CustomerBookingDto createCustomerBooking(
            CustomerBookingDto dto) {

        Customer customer =
                customerRepository.findById(dto.getCustomerId())
                        .orElse(null);

        Booking booking =
                bookingRepository.findById(dto.getBookingId())
                        .orElse(null);

        // Make sure both records exist
        if (customer == null || booking == null) {
            return null;
        }

        CustomerBookingId id =
                new CustomerBookingId(
                        dto.getCustomerId(),
                        dto.getBookingId());

        // Don't create the same relationship twice
        if (customerBookingRepository.existsById(id)) {
            return null;
        }

        CustomerBooking customerBooking =
                new CustomerBooking();

        customerBooking.setId(id);
        customerBooking.setCustomer(customer);
        customerBooking.setBooking(booking);

        CustomerBooking saved =
                customerBookingRepository.save(customerBooking);

        return customerBookingMapper.toDto(saved);
    }

    // Get all bookings belonging to one customer
    public List<CustomerBookingDto> getBookingsForCustomer(
            Integer customerId) {

        return customerBookingRepository
                .findByCustomerId(customerId)
                .stream()
                .map(customerBookingMapper::toDto)
                .collect(Collectors.toList());
    }

    // Get all customers belonging to one booking
    public List<CustomerBookingDto> getCustomersForBooking(
            Integer bookingId) {

        return customerBookingRepository
                .findByBookingId(bookingId)
                .stream()
                .map(customerBookingMapper::toDto)
                .collect(Collectors.toList());
    }

    // Delete a customer-booking relationship
    public boolean deleteCustomerBooking(
            Integer customerId,
            Integer bookingId) {

        CustomerBookingId id =
                new CustomerBookingId(customerId, bookingId);

        if (!customerBookingRepository.existsById(id)) {
            return false;
        }

        customerBookingRepository.deleteById(id);
        return true;
    }
}