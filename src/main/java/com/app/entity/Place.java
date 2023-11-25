package com.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PLACE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Place extends BaseEntity {
    private String name;
    private String address;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private String website;
    private String hotline;
    private String type;
    private String image;
    @Column(name = "CLOUDINARY_ID")
    private String cloudinaryId;
}
