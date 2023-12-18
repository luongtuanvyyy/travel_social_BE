package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountData {
    Integer id;
    String name;
    String avatar;
    Boolean isVerify;
    String email;
    Long followerCount;

    public AccountData (Integer id, String name, String avatar, Boolean isVerify, String email) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.isVerify = isVerify;
        this.email = email;
    }
}

