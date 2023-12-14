package com.app.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class BlogReaction extends BaseEntity{

    private boolean share;
    private String comment;

    @Column(name = "REACTION_LIKE")
    private boolean reactionLike;


    @ManyToOne
    @JoinColumn(name = "BLOG_ID", referencedColumnName = "ID")
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID")
    private Account account;
}
