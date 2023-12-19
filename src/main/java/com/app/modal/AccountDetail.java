package com.app.modal;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetail {
    private String name;

    private String avatar;
    
    private String email;

    private long totalBlog;

    private long totalLike;

    private long totalFollow;



}
