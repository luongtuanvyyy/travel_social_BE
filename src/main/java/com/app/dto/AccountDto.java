package com.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.app.type.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Integer id;
    private String accountName;
    private String address;
    private String avatar;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthday;
    private String cloudinaryId;
    private String description;
    private String email;
    private String firstName;
    private boolean gender;
    private String hotline;
    private String lastName;
    private ERole role;
    private boolean isVerify = false;
    private boolean vip;
    private Boolean isActivated;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdAt;
    private String createdBy;
}
