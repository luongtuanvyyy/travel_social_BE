package com.app.service;

import com.app.entity.Follow;
import com.app.entity.Review;
import com.app.payload.request.ReviewQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewServices {
    APIResponse filterReview(ReviewQueryParam reviewQueryParam);

    APIResponse create(Review review, MultipartFile image);
    APIResponse update(Review review,MultipartFile image);
    APIResponse delete(Integer id);

    APIResponse findByReviewTour(Integer id);
    APIResponse uploadExcel(MultipartFile excel);

    APIResponse createBatch(List<Review> reviews);
}
