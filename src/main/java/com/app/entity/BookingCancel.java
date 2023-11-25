package com.app.entity;

import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "BOOKING_CANCEL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BookingCancel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal currency;

    @ManyToOne
    @JoinColumn(name = "BOOKING_ID", referencedColumnName = "ID")
    private Booking bookingId;
}
