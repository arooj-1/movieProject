package com.sparta.movieproject.controller;

import com.sparta.movieproject.controllers.CustomerController;
import com.sparta.movieproject.dto.CustomerDto;
import com.sparta.movieproject.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    private CustomerService customerService;
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        customerService = Mockito.mock(CustomerService.class);

        customerController =
                new CustomerController(customerService);
    }

    @Test
    void getAllCustomers() {

        var customer1 = new CustomerDto();
        var customer2 = new CustomerDto();

        var customerList = List.of(customer1, customer2);

        when(customerService.getAllCustomers())
                .thenReturn(customerList);

        ResponseEntity<List<CustomerDto>> result =
                customerController.getAllCustomers();

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(customerList, result.getBody());
        assertEquals(2, result.getBody().size());

        verify(customerService).getAllCustomers();
    }

    @Test
    void getCustomerById() {

        Integer testId = 1;

        var customerDto = new CustomerDto();

        when(customerService.getCustomerById(testId))
                .thenReturn(customerDto);

        ResponseEntity<CustomerDto> result =
                customerController.getCustomerById(testId);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(customerDto, result.getBody());

        verify(customerService)
                .getCustomerById(testId);
    }

    @Test
    void createCustomer() {

        var customerDto = new CustomerDto();
        var createdCustomer = new CustomerDto();

        when(customerService.createCustomer(customerDto)).thenReturn(createdCustomer);

        ResponseEntity<CustomerDto> result = customerController.createCustomer(customerDto);

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(createdCustomer, result.getBody());

        verify(customerService).createCustomer(customerDto);
    }

    @Test
    void updateCustomer() {

        Integer testId = 1;

        var customerDto = new CustomerDto();
        var updatedCustomer = new CustomerDto();

        when(customerService.updateCustomer(testId, customerDto)).thenReturn(updatedCustomer);

        ResponseEntity<CustomerDto> result = customerController.updateCustomer(testId, customerDto);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(updatedCustomer, result.getBody());

        verify(customerService).updateCustomer(testId, customerDto);
    }

    @Test
    void deleteCustomer() {

        Integer testId = 1;

        doNothing().when(customerService).deleteCustomer(testId);

        ResponseEntity<Void> result = customerController.deleteCustomer(testId);

        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());

        verify(customerService).deleteCustomer(testId);
    }
}