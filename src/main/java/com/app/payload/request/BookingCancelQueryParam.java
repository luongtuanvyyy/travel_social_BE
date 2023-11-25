package com.app.payload.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BookingCancelQueryParam extends BaseQueryRequest{
        Integer id;
        BigDecimal currency;
        String status;
        Integer Booking_id;
}
