package com.app.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "REVIEW")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Review extends BaseEntity {
    private Float rating;
    private String image;

    @Column(name = "CLOUDINARY_ID")
    private String cloudinaryId;

    @ManyToOne
    @JoinColumn(name = "TOUR_ID", referencedColumnName = "ID")
    private Tour tourId;

    @ManyToOne
    @JoinColumn(name = "REVIEW_ID", referencedColumnName = "ID")
    private Review reviewId;
}
