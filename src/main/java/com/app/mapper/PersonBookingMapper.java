package com.app.mapper;

import com.app.dto.BookingDto;
import com.app.dto.PersonBookingDto;
import com.app.entity.Booking;
import com.app.entity.PersonBooking;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonBookingMapper {
    @Autowired
    ModelMapper modelMapper;

    public PersonBooking toPersonBooking(BookingDto dto) {
        return dto == null ? null : modelMapper.map(dto, PersonBooking.class);
    }

    public PersonBookingDto personDto(PersonBooking personBooking) {
        return personBooking == null ? null : modelMapper.map(personBooking, PersonBookingDto.class);
    }
}
