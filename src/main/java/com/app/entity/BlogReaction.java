package com.app.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "BLOG_REACTION")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BlogReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean share;
    private String comment;

    @Column(name = "REACTION_LIKE")
    private boolean reactionLike;
    @Column(name = "CREATE_TIME")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createTime;

    @ManyToOne
    @JoinColumn(name = "BLOG_ID", referencedColumnName = "ID")
    @JsonIgnore
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID")
    @JsonIgnore
    private Account account;
}
