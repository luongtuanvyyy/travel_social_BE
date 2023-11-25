package com.app.entity;

import javax.persistence.*;
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
    private Tour tourId;
}
