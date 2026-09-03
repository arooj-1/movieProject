package com.sparta.movieproject.mappers;

import com.sparta.movieproject.dto.CustomerBookingDto;
import com.sparta.movieproject.entities.CustomerBooking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerBookingMapper {
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "booking.id", target = "bookingId")
    CustomerBookingDto toDto(CustomerBooking customerBooking);

}
