package com.app.repository;


import com.app.entity.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogCommentRepository extends JpaRepository<BlogComment, Integer> {
    Optional<BlogComment> findById(Integer id);
}

