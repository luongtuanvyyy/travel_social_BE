package com.app.api;

import com.app.entity.PersonBooking;
import com.app.entity.Review;
import com.app.payload.request.ReviewQueryParam;
import com.app.payload.response.APIResponse;
import com.app.service.ReviewServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewApi {
    @Autowired
    ReviewServices reviewServices;

    @GetMapping("/public/reviews")
    public ResponseEntity<?> getAllReview(ReviewQueryParam reviewQueryParam) {
        return ResponseEntity.ok(reviewServices.filterReview(reviewQueryParam));
    }

    @GetMapping("/public/reviews/findByTourId")
    public ResponseEntity<?> findByTourId(@RequestParam(name = "id") Integer id, ReviewQueryParam reviewQueryParam) {
        return ResponseEntity.ok(reviewServices.findReviewByTourId(id,reviewQueryParam));
    }

    @GetMapping("/public/reviews/findByRating")
    public ResponseEntity<?> findByRating(@RequestParam(name = "rating") Float rating, ReviewQueryParam reviewQueryParam) {
        return ResponseEntity.ok(reviewServices.findReviewByRatting(rating,reviewQueryParam));
    }

    @PostMapping("/user/reviews")
    public ResponseEntity<?> createReview(@RequestPart(name = "review") Review review) {
        APIResponse response = reviewServices.create(review);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/user/reviews")
    public ResponseEntity<?> updateReview(@RequestPart(name = "review") Review review) {
        APIResponse response = reviewServices.update(review);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/user/reviews")
    public ResponseEntity<?> deleteReview(@RequestParam("id") Integer id) {
        APIResponse response = reviewServices.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/user/reviews/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = reviewServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/user/reviews/batch")
    public ResponseEntity<?> createReviewsBatch(@RequestBody List<Review> reviews) {
        APIResponse response = reviewServices.createBatch(reviews);
        return ResponseEntity.ok().body(response);
    }
}
