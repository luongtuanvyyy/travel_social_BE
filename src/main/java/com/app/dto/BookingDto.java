package com.app.dto;

import com.app.entity.Account;
import com.app.entity.Payment;
import com.app.entity.Place;
import com.app.entity.Tour;
import com.app.type.EBooking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private String note;
    private Integer adult;
    private Integer children;
    private Integer baby;
    private String qr;
    private EBooking status;
    private Account accountId;
    private Tour tourId;
    private Payment paymentId;
    private List<PersonBookingDto> personBookings;
}
