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
    private int id;
    private boolean isActivated;
    private boolean isVerify;
    private String cloudinaryId;
    private String description;
    private String image;
    private String avatar;
    private String name;
    private String createdBy;
    private String modifiedBy;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date modifiedAt;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date createdAt;
    private long totalLike;
    private long totalComment;
    private long totalShare;

}
