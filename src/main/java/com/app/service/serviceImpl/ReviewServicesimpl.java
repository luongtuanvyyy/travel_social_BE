package com.app.service.serviceImpl;

import com.app.entity.Review;
import com.app.modal.ReviewModel;
import com.app.payload.request.ReviewQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.ReviewRepository;
import com.app.service.ReviewServices;
import com.app.utils.PageUtils;
import com.app.utils.RequestParamsUtils;
import com.app.speficication.ReviewSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServicesimpl implements ReviewServices {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewSpecification reviewSpecification;

    @Autowired
    RequestParamsUtils requestParamsUtils;

    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ImportExcelService importExcelService;

    @Override
    public APIResponse filterReview(ReviewQueryParam reviewQueryParam) {
        try {
        Specification<Review> spec = reviewSpecification.getReviewSpecification(reviewQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(reviewQueryParam);
        Page<Review> response = reviewRepository.findAll(spec, pageable);
            if (response.isEmpty()) {
                return new APIResponse(false, "No data found");
            } else {
                return new APIResponse(PageUtils.toPageResponse(response));
            }
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse create(Review review) {
        try {
        review = reviewRepository.save(review);
        return new SuccessAPIResponse(review);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse update(Review review) {
        try {
        if(review == null){
            return  new FailureAPIResponse("Review id is required!");
        }
        Review exists = reviewRepository.findById(review.getId()).orElse(null);
        if(exists == null){
            return  new FailureAPIResponse("Cannot find review with id: "+review.getId());
        }
        review = reviewRepository.save(review);
        return new SuccessAPIResponse(review);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse delete(Integer id) {
        try {
            reviewRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse findReviewByTourId(Integer id, ReviewQueryParam reviewQueryParam) {
        try {
            Pageable pageable = requestParamsUtils.getPageable(reviewQueryParam);
            Page<ReviewModel> response = reviewRepository.findReviewByTourId(id, pageable);
            if (response.isEmpty()) {
                return new APIResponse(false, "No data found");
            } else {
                return new APIResponse(PageUtils.toPageResponse(response));
            }
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse findReviewByRatting(Float ratting, ReviewQueryParam reviewQueryParam) {
        try {
            Pageable pageable = requestParamsUtils.getPageable(reviewQueryParam);
            Page<Review> response = reviewRepository.findByRating(ratting, pageable);
            if (response.isEmpty()) {
                return new APIResponse(false, "No data found");
            } else {
                return new APIResponse(PageUtils.toPageResponse(response));
            }
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }
    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, Review.class, reviewRepository);
    }

    @Override
    public APIResponse createBatch(List<Review> reviews) {
        List<Review> createdReviews = new ArrayList<>();
        for (Review review : reviews) {
            Review createdReview = reviewRepository.save(review);
            createdReviews.add(createdReview);
        }
        return new SuccessAPIResponse(createdReviews);
    }
}
