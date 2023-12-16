package com.app.repository;


import com.app.entity.Blog;
import com.app.entity.BlogLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogLikeRepository extends JpaRepository<BlogLike, Integer> {
    BlogLike findByBlogAndCreatedBy(Blog blog, String createdBy);
}

