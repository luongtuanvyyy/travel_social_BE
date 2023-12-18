package com.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.app.type.EAuthProvider;
import com.app.type.ERole;
import io.jsonwebtoken.Claims;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {
    private String name;
    private String address;
    private String avatar;
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
    @Column(name = "cloudinary_id")
    private String cloudinaryId;
    private String description;
    @Email
    @NotBlank
    private String email;
    private boolean gender;
    private String phone;
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


}
