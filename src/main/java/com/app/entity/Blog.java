package com.app.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "BLOG")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Blog extends BaseEntity {
    private String title;
    private String image;
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String avatar;

    private String name;

    @Column(name = "IS_VERIFY")
    private boolean isVerify;

    @Column(name = "CLOUDINARY_ID")
    private String cloudinaryId;

    @ManyToOne
    @JoinColumn(name = "PLACE_ID", referencedColumnName = "ID")
    private Place placeId;

    @ManyToOne
    @JoinColumn(name = "TOUR_ID", referencedColumnName = "ID")
    private Tour tourId;

}
