package com.app.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "TOUR_DETAIL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TourDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "TOUR_ID", referencedColumnName = "id")
    @JsonBackReference
    private Tour tour;

    @ManyToOne
    @JoinColumn(name = "PLACE_ID", referencedColumnName = "id")
    private Place placeId;

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID", referencedColumnName = "id")
    private Hotel hotelId;

    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "id")
    private Restaurant restaurantId;

    @ManyToOne
    @JoinColumn(name = "VEHICLE_ID", referencedColumnName = "id")
    private Vehicle vehicleId;
}
