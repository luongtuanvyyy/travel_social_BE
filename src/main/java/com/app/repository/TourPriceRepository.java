package com.app.repository;

import com.app.entity.TourPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourPriceRepository extends JpaRepository<TourPrice, Integer>{
    Page<TourPrice> findAll(Specification<TourPrice> spec, Pageable pageable);
}
