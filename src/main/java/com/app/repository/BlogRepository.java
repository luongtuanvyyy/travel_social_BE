package com.app.repository;

import com.app.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {


//    @EntityGraph("graph.product")
    Page<Blog> findAll(Specification<Blog> spec, Pageable pageable);
    @Query("SELECT COUNT(b) FROM Blog b WHERE b.createdBy = :createdBy")
    Integer countByCreatedBy(@Param("createdBy") String createdBy);
    @Query("SELECT b FROM Blog b ORDER BY b.createdAt DESC")
    Page<Blog> findLatestBlogs(Specification<Blog> spec, Pageable pageable);

    @Query("SELECT b FROM Blog b WHERE b.id NOT IN (SELECT v.blogId FROM View v)")
    Page<Blog>  findAllNotSeen(Specification<Blog> spec, Pageable pageable);

}

