package com.app.repository;

import com.app.entity.Review;
import com.app.modal.ReviewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Optional<Review> findByRating(Float rating);

    // @EntityGraph("graph.product")
    Page<Review> findAll(Specification<Review> spec, Pageable pageable);


    @Query("SELECT new com.app.modal.ReviewModel(" +
            "a.name, a.avatar, a.email, r.comment, r.rating) " +
            "FROM Tour t " +
            "JOIN Review r ON t.id = r.tourId.id " +
            "JOIN Account a ON r.createdBy = a.email " +
            "WHERE t.id = :tourId")
    Page<ReviewModel> findReviewByTourId(Integer tourId, Pageable pageable);




}
