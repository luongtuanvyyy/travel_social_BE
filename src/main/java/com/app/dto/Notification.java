package com.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private Integer id;
    private String name;
    private String address;
    private String avatar;
    private String email;
    private boolean gender;
    private boolean isVerify;
    private boolean vip;
    @JsonFormat(pattern = "MM-dd-yyyy")
    private Date createdAt;
    
    private String notificationType;
    private String description;
}
