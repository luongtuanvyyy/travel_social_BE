package com.app.entity;

import com.app.type.EBlogNotification;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "BLOG_NOTIFICATION")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BlogNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOTIFICATION_TYPE")
    private EBlogNotification notificationType;

    @Column(name = "CREATE_TIME")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createTime;

    @ManyToOne
    @JoinColumn(name = "BLOG_ID")
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID")
    @JsonIgnore
    private Account account;
}
