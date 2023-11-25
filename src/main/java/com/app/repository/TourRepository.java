package com.app.repository;

import com.app.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Integer> {
    Page<Tour> findAll(Specification<Tour> spec, Pageable pageable);



}



