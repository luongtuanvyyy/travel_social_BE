package com.app.repository;


import com.app.entity.Blog;
import com.app.entity.BlogComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogCommentRepository extends JpaRepository<BlogComment, Integer> {
    Optional<BlogComment> findById(Integer id);
    List<BlogComment> findByBlog (Blog blog);

    Page<BlogComment> findByBlog (Blog blog, Pageable pageable);
    List<BlogComment> findByBlogOrderByCreatedAtDesc (Blog blog);
    long countByBlog(Blog blog);
}

