package com.sparta.movieproject.mappers;

import com.sparta.movieproject.dto.CustomerDto;
import com.sparta.movieproject.entities.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto toDto(Customer customer);

    Customer toEntity(CustomerDto customerDto);
}