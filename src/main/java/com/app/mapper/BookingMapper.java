package com.app.mapper;

import com.app.dto.BookingDto;
import com.app.entity.Booking;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    @Autowired
    ModelMapper modelMapper;

    public Booking toBooking(BookingDto dto) {
        return dto == null ? null : modelMapper.map(dto, Booking.class);
    }

    public BookingDto bookingDto(Booking booking) {
        return booking == null ? null : modelMapper.map(booking, BookingDto.class);
    }
}
