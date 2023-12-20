package com.app.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

@Entity
@Table(name = "BLOG")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Blog extends BaseEntity {
    private String image;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    @Column(name = "IS_VERIFY")
    private boolean isVerify;
    @Column(name = "CLOUDINARY_ID")
    private String cloudinaryId;
    @ManyToOne
    @JoinColumn(name = "PLACE_ID", referencedColumnName = "ID")
    private Place place;
    @ManyToOne
    @JoinColumn(name = "TOUR_ID", referencedColumnName = "ID")
    @JsonBackReference
    private Tour tour;

}
