package com.app.repository;

import com.app.entity.TourTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourTemplateRepository extends JpaRepository<TourTemplate, Integer> {
    Page<TourTemplate> findAll(Specification<TourTemplate> spec, Pageable pageable);



}
