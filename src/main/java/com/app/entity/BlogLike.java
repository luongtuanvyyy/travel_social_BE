package com.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BLOG_LIKE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BlogLike extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "BLOG_ID", referencedColumnName = "ID")
    @JsonIgnore
    private Blog blog;
}