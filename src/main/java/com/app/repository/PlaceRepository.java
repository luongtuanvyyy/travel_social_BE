package com.app.repository;

import com.app.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {
    Page<Place> findAll(Specification<Place> spec, Pageable pageable);

//    Page<Place> finbyname(Specification<Place> spec, Pageable pageable, String name);


}

