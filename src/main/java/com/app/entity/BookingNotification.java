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
public class BookingNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOTIFICATION_TYPE")
    private EBooking notificationType;

    @Column(name = "CREATE_TIME")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createTime;

    @ManyToOne
    @JoinColumn(name = "BOOKING_ID", referencedColumnName = "ID")
    @JsonIgnore
    private Booking bookingId;
}
