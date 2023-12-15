package com.app.entity;

import com.app.type.EBooking;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "BOOKING_NOTIFICATION")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BookingNotification extends BaseEntity {
    @Column(name = "NOTIFICATION_TYPE")
    private EBooking notificationType;

    @ManyToOne
    @JoinColumn(name = "BOOKING_ID", referencedColumnName = "ID")
    @JsonIgnore
    private Booking bookingId;
}
