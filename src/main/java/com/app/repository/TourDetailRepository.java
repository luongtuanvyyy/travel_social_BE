package com.app.repository;

import com.app.entity.TourDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourDetailRepository extends JpaRepository<TourDetail, Integer>{
    Page<TourDetail> findAll(Specification<TourDetail> spec, Pageable pageable);
}
