package com.app.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

@Entity
@Table(name = "TOUR_TEMPLATE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TourTemplate extends BaseEntity{
    private String name;
    private String description;
}
