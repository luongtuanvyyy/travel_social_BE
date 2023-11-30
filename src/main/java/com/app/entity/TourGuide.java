package com.app.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

@Entity
@Table(name = "TOUR_GUIDE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TourGuide extends BaseEntity {
    private String fullName;
    private boolean gender;
    private String avatar;

    @Column(name = "CLOUDINARY_ID")
    private String cloudinaryId;

}
