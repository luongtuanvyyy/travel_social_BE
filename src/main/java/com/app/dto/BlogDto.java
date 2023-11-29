package com.app.dto;

import com.app.entity.Place;
import com.app.entity.Tour;
import com.app.type.ERole;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDto {
    private Integer id;
    private String title;
    private String image;
    private String description;
    private String avatar;
    private String name;
    private boolean isVerify;
    private Place placeId;
    private Float sumLike;
    private Float sumCmt;
    private Tour tour;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdAt;
    private String createdBy;
    private String ggProviderId;
    private String fbProviderId;
    private Integer totalLike;
    private Integer totalFollow;
    private Integer totalBlog;
}
