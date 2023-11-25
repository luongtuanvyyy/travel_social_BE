package com.app.repository;

import com.app.entity.TourGuide;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TuorGuideRepository extends JpaRepository<TourGuide, Integer> {
    Page<TourGuide> findAll(Specification<TourGuide> spec, Pageable pageable);
}
