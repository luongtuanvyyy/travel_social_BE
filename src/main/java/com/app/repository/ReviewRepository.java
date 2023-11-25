package com.app.repository;

import com.app.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Optional<Review> findByRating(Float rating);

    // @EntityGraph("graph.product")
    Page<Review> findAll(Specification<Review> spec, Pageable pageable);
    // @Query("SELECT u FROM Review u WHERE u.tour.id =:id")
    // List<Review> findByReviewTour(Integer id);
}
