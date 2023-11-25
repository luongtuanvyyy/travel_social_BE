package com.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

@Entity
@Table(name = "VEHICLE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Vehicle extends BaseEntity {
    private String address;
    private String name;
    private String hotline;
    private String type;
    private String website;
        private String email;
    private String image;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    @Column(name = "CLOUDINARY_ID")
    private String cloudinaryId;
}
