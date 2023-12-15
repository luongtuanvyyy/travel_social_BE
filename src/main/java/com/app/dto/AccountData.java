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

    public AccountData(Integer id, String name, String avatar, String email) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.email = email;
    }

    public AccountData(Integer id, String name, String avatar, boolean isVerify) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.isVerify = isVerify;
    }
}