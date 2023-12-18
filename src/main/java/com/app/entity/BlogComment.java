package com.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BLOG_COMMENT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BlogComment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "BLOG_ID", referencedColumnName = "ID")
    @JsonIgnore
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "PARENT_COMMENT_ID")
    @JsonIgnore
    private BlogComment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BlogComment> replies;

    private String content;
}