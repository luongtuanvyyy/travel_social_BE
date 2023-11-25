package com.app.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "TOUR_PRICE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TourPrice extends BaseEntity {
    private Integer adult;
    private Integer children;
    private Integer baby;

    @ManyToOne
    @JoinColumn(name = "TOUR_ID", referencedColumnName = "ID")
    private Tour tourId;
}
