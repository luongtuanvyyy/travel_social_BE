package com.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCommentDto {
    private String avatar;
    private String name;
    private String content;
    @JsonFormat(pattern = "MM-dd-yyyy")
    private Date createdAt;
    private Integer id;
    private boolean isVerify;
    private boolean vip;

    private List<AccountCommentDto> replies;
}
