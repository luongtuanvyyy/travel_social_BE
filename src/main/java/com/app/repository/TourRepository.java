package com.app.repository;

import com.app.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Integer> {
    Page<Tour> findAll(Specification<Tour> spec, Pageable pageable);
    @Query("SELECT t FROM Tour t WHERE t.discount IS NOT NULL")
    Page<Tour> DiscountIsNotNull(Specification<Tour> spec, Pageable pageable);;

}



