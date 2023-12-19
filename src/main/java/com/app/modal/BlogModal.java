package com.app.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.stream.events.Comment;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogModal {
    private int id;
    private Date createdAt;
    private String createdBy;
    private boolean isActivated;
    private Date modifiedAt;
    private String modifiedBy;
    private String cloudinaryId;
    private String description;
    private String image;
    private String avatar;
    private String name;
    private boolean isVerify;
    private boolean isLike;
    private long totalLike;
    private long totalComment;
    private long totalShare;


}
