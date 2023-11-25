package com.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

@Entity
@Table(name = "HOTEL")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Hotel extends BaseEntity {
    private String name;
    private String address;
    private int room;
    private String hotline;
    private String type;
    private float rating;
    private String image;
    @Column(name = "CLOUDINARY_ID")
    private String cloudinaryId;
}
