package com.sparta.movieproject.controller;

import com.sparta.movieproject.controllers.CustomerBookingController;
import com.sparta.movieproject.dto.CustomerBookingDto;
import com.sparta.movieproject.services.CustomerBookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerBookingControllerTest {

    private CustomerBookingService customerBookingService;
    private CustomerBookingController customerBookingController;

    @BeforeEach
    void setUp() {
        customerBookingService = Mockito.mock(CustomerBookingService.class);

        customerBookingController =
                new CustomerBookingController(customerBookingService);
    }

    @Test
    void getAllCustomerBookings() {

        var dto1 = new CustomerBookingDto();
        var dto2 = new CustomerBookingDto();

        var customerBookingList = List.of(dto1, dto2);

        when(customerBookingService.getAllCustomerBookings())
                .thenReturn(customerBookingList);

        ResponseEntity<List<CustomerBookingDto>> result =
                customerBookingController.getAllCustomerBookings();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(customerBookingList, result.getBody());
        assertEquals(2, result.getBody().size());

        verify(customerBookingService)
                .getAllCustomerBookings();
    }

    @Test
    void getCustomerBooking() {

        Integer customerId = 1;
        Integer bookingId = 10;

        var dto = new CustomerBookingDto();

        when(customerBookingService.getCustomerBooking(
                customerId,
                bookingId))
                .thenReturn(dto);

        ResponseEntity<?> result =
                customerBookingController.getCustomerBooking(
                        customerId,
                        bookingId);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(dto, result.getBody());

        verify(customerBookingService)
                .getCustomerBooking(customerId, bookingId);
    }

    @Test
    void getCustomerBookingReturnsNotFoundWhenBookingDoesNotExist() {

        Integer customerId = 1;
        Integer bookingId = 10;

        when(customerBookingService.getCustomerBooking(
                customerId,
                bookingId))
                .thenReturn(null);

        ResponseEntity<?> result =
                customerBookingController.getCustomerBooking(
                        customerId,
                        bookingId);

        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());

        verify(customerBookingService)
                .getCustomerBooking(customerId, bookingId);
    }

    @Test
    void createCustomerBooking() {

        Integer customerId = 1;
        Integer bookingId = 10;

        var inputDto = new CustomerBookingDto();
        inputDto.setCustomerId(customerId);
        inputDto.setBookingId(bookingId);

        var createdDto = new CustomerBookingDto();
        createdDto.setCustomerId(customerId);
        createdDto.setBookingId(bookingId);

        when(customerBookingService.createCustomerBooking(inputDto))
                .thenReturn(createdDto);

        ResponseEntity<?> result =
                customerBookingController.createCustomerBooking(inputDto);

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(createdDto, result.getBody());

        verify(customerBookingService)
                .createCustomerBooking(inputDto);
    }

    @Test
    void createCustomerBookingReturnsConflictWhenCreationFails() {

        Integer customerId = 1;
        Integer bookingId = 10;

        var inputDto = new CustomerBookingDto();
        inputDto.setCustomerId(customerId);
        inputDto.setBookingId(bookingId);

        when(customerBookingService.createCustomerBooking(inputDto))
                .thenReturn(null);

        ResponseEntity<?> result =
                customerBookingController.createCustomerBooking(inputDto);

        assertNotNull(result);
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals(
                "Unable to create customer booking",
                result.getBody()
        );

        verify(customerBookingService)
                .createCustomerBooking(inputDto);
    }

    @Test
    void getBookingsForCustomer() {

        Integer customerId = 1;

        var dto1 = new CustomerBookingDto();
        var dto2 = new CustomerBookingDto();

        var bookingList = List.of(dto1, dto2);

        when(customerBookingService.getBookingsForCustomer(customerId))
                .thenReturn(bookingList);

        ResponseEntity<List<CustomerBookingDto>> result =
                customerBookingController.getBookingsForCustomer(
                        customerId);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(bookingList, result.getBody());
        assertEquals(2, result.getBody().size());

        verify(customerBookingService)
                .getBookingsForCustomer(customerId);
    }

    @Test
    void getCustomersForBooking() {

        Integer bookingId = 10;

        var dto1 = new CustomerBookingDto();
        var dto2 = new CustomerBookingDto();

        var customerList = List.of(dto1, dto2);

        when(customerBookingService.getCustomersForBooking(bookingId))
                .thenReturn(customerList);

        ResponseEntity<List<CustomerBookingDto>> result =
                customerBookingController.getCustomersForBooking(
                        bookingId);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(customerList, result.getBody());
        assertEquals(2, result.getBody().size());

        verify(customerBookingService)
                .getCustomersForBooking(bookingId);
    }

    @Test
    void deleteCustomerBooking() {

        Integer customerId = 1;
        Integer bookingId = 10;

        when(customerBookingService.deleteCustomerBooking(
                customerId,
                bookingId))
                .thenReturn(true);

        ResponseEntity<?> result =
                customerBookingController.deleteCustomerBooking(
                        customerId,
                        bookingId);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());

        verify(customerBookingService)
                .deleteCustomerBooking(customerId, bookingId);
    }

    @Test
    void deleteCustomerBookingReturnsNotFoundWhenRelationshipDoesNotExist() {

        Integer customerId = 1;
        Integer bookingId = 10;

        when(customerBookingService.deleteCustomerBooking(
                customerId,
                bookingId))
                .thenReturn(false);

        ResponseEntity<?> result =
                customerBookingController.deleteCustomerBooking(
                        customerId,
                        bookingId);

        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());

        verify(customerBookingService)
                .deleteCustomerBooking(customerId, bookingId);
    }
}


