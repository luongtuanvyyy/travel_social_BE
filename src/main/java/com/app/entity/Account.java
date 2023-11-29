package com.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.app.type.EAuthProvider;
import com.app.type.ERole;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {
    @Column(name = "ACCOUNT_NAME")
    private String accountName;
    private String name;
    private String address;
    private String avatar;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @Column(name = "cloudinary_id")
    private String cloudinaryId;
    private String description;
    @Email
    @NotBlank
    private String email;
    @Column(name = "FIRST_NAME")
    private String firstName;
    private boolean gender;
    private String hotline;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "LOGIN_TYPE")
    private String loginType;
    private ERole role = ERole.ROLE_USER;
    @Column(name = "IS_VERIFY")
    private boolean isVerify = false;
    private boolean vip;
    private String password;
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private EAuthProvider provider;
    @JsonIgnore
    private String providerId;

    private String ggProviderId;
    private String fbProviderId;
}
