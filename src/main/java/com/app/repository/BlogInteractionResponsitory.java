package com.app.repository;

import com.app.entity.BlogReaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogInteractionResponsitory extends JpaRepository<BlogReaction, Integer> {

    Page<BlogReaction> findAll(Specification<BlogReaction> spec, Pageable pageable);
}
