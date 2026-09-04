package com.sparta.movieproject.services;

import com.sparta.movieproject.dto.CustomerBookingDto;
import com.sparta.movieproject.entities.Booking;
import com.sparta.movieproject.entities.Customer;
import com.sparta.movieproject.entities.CustomerBooking;
import com.sparta.movieproject.entities.CustomerBookingId;
import com.sparta.movieproject.mappers.CustomerBookingMapper;
import com.sparta.movieproject.repository.BookingRepository;
import com.sparta.movieproject.repository.CustomerBookingRepository;
import com.sparta.movieproject.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerBookingServiceTest {

    private CustomerBookingRepository customerBookingRepository;
    private CustomerRepository customerRepository;
    private BookingRepository bookingRepository;
    private CustomerBookingMapper customerBookingMapper;
    private CustomerBookingService customerBookingService;

    @BeforeEach
    void setUp() {
        customerBookingRepository = Mockito.mock(CustomerBookingRepository.class);
        customerRepository = Mockito.mock(CustomerRepository.class);
        bookingRepository = Mockito.mock(BookingRepository.class);
        customerBookingMapper = Mockito.mock(CustomerBookingMapper.class);

        customerBookingService = new CustomerBookingService(customerBookingRepository, customerRepository, bookingRepository, customerBookingMapper
        );
    }

    @Test
    void getAllCustomerBookings() {

        var customerBooking1 = new CustomerBooking();
        var customerBooking2 = new CustomerBooking();

        var customerBookingList = List.of(customerBooking1, customerBooking2);

        var dto1 = new CustomerBookingDto();
        var dto2 = new CustomerBookingDto();

        when(customerBookingRepository.findAll()).thenReturn(customerBookingList);
        when(customerBookingMapper.toDto(customerBooking1)).thenReturn(dto1);
        when(customerBookingMapper.toDto(customerBooking2)).thenReturn(dto2);

        var result = customerBookingService.getAllCustomerBookings();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(0) instanceof CustomerBookingDto);
        assertTrue(result.get(1) instanceof CustomerBookingDto);

        verify(customerBookingRepository).findAll();
        verify(customerBookingMapper, times(2))
                .toDto(any(CustomerBooking.class));
    }

    @Test
    void getCustomerBooking() {

        Integer customerId = 1;
        Integer bookingId = 10;

        CustomerBookingId id = new CustomerBookingId(customerId, bookingId);

        var customerBooking = new CustomerBooking();
        var dto = new CustomerBookingDto();

        when(customerBookingRepository.findById(id)).thenReturn(Optional.of(customerBooking));

        when(customerBookingMapper.toDto(customerBooking)).thenReturn(dto);

        var result = customerBookingService.getCustomerBooking(customerId, bookingId);

        assertNotNull(result);
        assertEquals(dto, result);

        verify(customerBookingRepository).findById(id);
        verify(customerBookingMapper).toDto(customerBooking);
    }

    @Test
    void getCustomerBookingReturnsNullWhenNotFound() {

        Integer customerId = 1;
        Integer bookingId = 10;

        CustomerBookingId id = new CustomerBookingId(customerId, bookingId);

        when(customerBookingRepository.findById(id)).thenReturn(Optional.empty());

        var result = customerBookingService.getCustomerBooking(customerId,bookingId);

        assertNull(result);

        verify(customerBookingRepository).findById(id);
        verify(customerBookingMapper, never())
                .toDto(any(CustomerBooking.class));
    }

    @Test
    void createCustomerBooking() {

        Integer customerId = 1;
        Integer bookingId = 10;

        var dto = new CustomerBookingDto();
        dto.setCustomerId(customerId);
        dto.setBookingId(bookingId);

        var customer = new Customer();
        var booking = new Booking();

        var savedCustomerBooking = new CustomerBooking();
        var outputDto = new CustomerBookingDto();

        CustomerBookingId id = new CustomerBookingId(customerId, bookingId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(customerBookingRepository.existsById(id)).thenReturn(false);
        when(customerBookingRepository.save(any(CustomerBooking.class))).thenReturn(savedCustomerBooking);
        when(customerBookingMapper.toDto(savedCustomerBooking)).thenReturn(outputDto);

        var result = customerBookingService.createCustomerBooking(dto);

        assertNotNull(result);
        assertEquals(outputDto, result);

        verify(customerRepository).findById(customerId);
        verify(bookingRepository).findById(bookingId);
        verify(customerBookingRepository).existsById(id);
        verify(customerBookingRepository).save(any(CustomerBooking.class));
        verify(customerBookingMapper).toDto(savedCustomerBooking);
    }

    @Test
    void createCustomerBookingReturnsNullWhenCustomerDoesNotExist() {

        Integer customerId = 1;
        Integer bookingId = 10;

        var dto = new CustomerBookingDto();
        dto.setCustomerId(customerId);
        dto.setBookingId(bookingId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(new Booking()));

        var result = customerBookingService.createCustomerBooking(dto);

        assertNull(result);

        verify(customerRepository).findById(customerId);
        verify(bookingRepository).findById(bookingId);
        verify(customerBookingRepository, never()).existsById(any(CustomerBookingId.class));
        verify(customerBookingRepository, never()).save(any(CustomerBooking.class));
        verify(customerBookingMapper, never()).toDto(any(CustomerBooking.class));
    }

    @Test
    void createCustomerBookingReturnsNullWhenBookingDoesNotExist() {

        Integer customerId = 1;
        Integer bookingId = 10;

        var dto = new CustomerBookingDto();
        dto.setCustomerId(customerId);
        dto.setBookingId(bookingId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(new Customer()));

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        var result = customerBookingService.createCustomerBooking(dto);

        assertNull(result);

        verify(customerRepository).findById(customerId);
        verify(bookingRepository).findById(bookingId);

        verify(customerBookingRepository, never()).existsById(any(CustomerBookingId.class));

        verify(customerBookingRepository, never()).save(any(CustomerBooking.class));

        verify(customerBookingMapper, never()).toDto(any(CustomerBooking.class));
    }

    @Test
    void createCustomerBookingReturnsNullWhenRelationshipAlreadyExists() {

        Integer customerId = 1;
        Integer bookingId = 10;

        var dto = new CustomerBookingDto();
        dto.setCustomerId(customerId);
        dto.setBookingId(bookingId);

        CustomerBookingId id = new CustomerBookingId(customerId, bookingId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(new Customer()));

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(new Booking()));

        when(customerBookingRepository.existsById(id)).thenReturn(true);

        var result = customerBookingService.createCustomerBooking(dto);

        assertNull(result);

        verify(customerRepository).findById(customerId);
        verify(bookingRepository).findById(bookingId);
        verify(customerBookingRepository).existsById(id);

        verify(customerBookingRepository, never()).save(any(CustomerBooking.class));

        verify(customerBookingMapper, never()).toDto(any(CustomerBooking.class));
    }

    @Test
    void getBookingsForCustomer() {

        Integer customerId = 1;

        var customerBooking1 = new CustomerBooking();
        var customerBooking2 = new CustomerBooking();

        var customerBookingList = List.of(customerBooking1, customerBooking2);

        var dto1 = new CustomerBookingDto();
        var dto2 = new CustomerBookingDto();

        when(customerBookingRepository.findByCustomerId(customerId)).thenReturn(customerBookingList);

        when(customerBookingMapper.toDto(customerBooking1)).thenReturn(dto1);

        when(customerBookingMapper.toDto(customerBooking2)).thenReturn(dto2);

        var result = customerBookingService.getBookingsForCustomer(customerId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));

        verify(customerBookingRepository).findByCustomerId(customerId);

        verify(customerBookingMapper, times(2)).toDto(any(CustomerBooking.class));
    }

    @Test
    void getCustomersForBooking() {

        Integer bookingId = 10;

        var customerBooking1 = new CustomerBooking();
        var customerBooking2 = new CustomerBooking();

        var customerBookingList = List.of(customerBooking1, customerBooking2);

        var dto1 = new CustomerBookingDto();
        var dto2 = new CustomerBookingDto();

        when(customerBookingRepository.findByBookingId(bookingId)).thenReturn(customerBookingList);

        when(customerBookingMapper.toDto(customerBooking1)).thenReturn(dto1);

        when(customerBookingMapper.toDto(customerBooking2)).thenReturn(dto2);

        var result = customerBookingService.getCustomersForBooking(bookingId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));

        verify(customerBookingRepository).findByBookingId(bookingId);

        verify(customerBookingMapper, times(2)).toDto(any(CustomerBooking.class));
    }

    @Test
    void deleteCustomerBooking() {

        Integer customerId = 1;
        Integer bookingId = 10;

        CustomerBookingId id = new CustomerBookingId(customerId, bookingId);

        when(customerBookingRepository.existsById(id)).thenReturn(true);

        var result = customerBookingService.deleteCustomerBooking(customerId, bookingId);

        assertTrue(result);

        verify(customerBookingRepository).existsById(id);
        verify(customerBookingRepository).deleteById(id);
    }

    @Test
    void deleteCustomerBookingReturnsFalseWhenNotFound() {

        Integer customerId = 1;
        Integer bookingId = 10;

        CustomerBookingId id = new CustomerBookingId(customerId, bookingId);

        when(customerBookingRepository.existsById(id)).thenReturn(false);

        var result = customerBookingService.deleteCustomerBooking(customerId,bookingId);
        assertFalse(result);
        verify(customerBookingRepository).existsById(id);
        verify(customerBookingRepository, never()).deleteById(any(CustomerBookingId.class));
    }
}
