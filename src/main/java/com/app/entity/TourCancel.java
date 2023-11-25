package com.app.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "TOUR_CANCEL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TourCancel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer percent;

    private Integer date;

    @ManyToOne
    @JoinColumn(name = "TOUR_ID", referencedColumnName = "ID")
    private Tour tour;
}
