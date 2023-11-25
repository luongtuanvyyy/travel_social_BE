package com.app.dto;

import com.app.entity.Tour;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime bookingDate;
    private String status;
    private String note;
    private Integer numberOfAdults;
    private Integer numberOfChildren;
    private Integer numberOfBabies;
    private BigDecimal adultPrice;
    private BigDecimal childrenPrice;
    private BigDecimal babyPrice;
    private BigDecimal totalPrice;
    private Integer userId;
    private Integer tourId;
    private Integer paymentId;
    private Tour tour;
}
