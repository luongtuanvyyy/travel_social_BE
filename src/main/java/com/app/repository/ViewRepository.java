package com.app.repository;

import com.app.entity.Account;
import com.app.entity.Blog;
import com.app.entity.View;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewRepository extends JpaRepository<View, Integer> {
    Page<View> findAll(Specification<View> spec, Pageable pageable);

}
