package com.app.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentModel {
    private String avatar;
    private String name;
    private String comment;
    private Date createdAt;
    private boolean isVerify;
}
