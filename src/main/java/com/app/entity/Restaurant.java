package com.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "RESTAURANT")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Restaurant extends BaseEntity {
    private String name;
    private String website;
    private String address;
    private String image;
    @Column(name = "CLOUDINARY_ID")
    private String cloudinaryId;
}
