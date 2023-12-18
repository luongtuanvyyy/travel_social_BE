package com.app.modal;

import com.app.type.EBlogNotification;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogNotification {
    private int id;
    private EBlogNotification notificationType;
    private String name;
    private String avatar;
    private Date createTime;
    private int blogId;
    private boolean isVerify = false;
}
