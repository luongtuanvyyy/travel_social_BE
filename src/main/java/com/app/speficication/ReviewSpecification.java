package com.app.speficication;

import com.app.entity.Review;
import com.app.payload.request.ReviewQueryParam;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ReviewSpecification {
    public Specification<Review> hasNameLike(Float keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("rating"),  keyword );
    }
    public Specification<Review> hasNameLikeid(Integer keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("tour_id"),  keyword );
    }

    public Specification<Review> getReviewSpecification(ReviewQueryParam reviewQueryParam) {
        Specification<Review> spec = Specification.where(null);
        if (reviewQueryParam.getRating() != null) {
            spec = spec.and(hasNameLike(reviewQueryParam.getRating()));
        }
        if (reviewQueryParam.getTour_id() != null) {
            spec = spec.and(hasNameLikeid(reviewQueryParam.getTour_id()));
        }

        return spec;
    }

}
