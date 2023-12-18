package com.app.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewModel {
    private String AccName;
    private String avatar;
    private String email;
    private String comment;
    private Float rating;
    private Boolean verify;
}
