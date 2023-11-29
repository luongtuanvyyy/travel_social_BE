package com.app.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

@Entity
@Table(name = "FAVORITE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Favorite extends BaseEntity {
    @Column(name = "NOTIFICATION_TYPE")
    private String notificationType;

    @ManyToOne
    @JoinColumn(name = "TOUR_ID", referencedColumnName = "ID")
    @JsonBackReference
    private Tour tour;
}
