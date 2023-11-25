package com.app.entity;

import com.app.type.EAges;
import javax.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "PERSON_BOOKING")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PersonBooking extends BaseEntity {
    private String fullName;
    private Date birthday;
    private boolean gender;
    private EAges ages;
    private String relationship;

    @Column(name = "IS_CANCEL")
    private boolean isCancel;
    @ManyToOne
    @JoinColumn(name = "BOOKING_ID", referencedColumnName = "ID")
    private Booking bookingId;
}
