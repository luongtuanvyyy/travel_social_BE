package com.app.speficication;

import com.app.entity.Favorite;
import com.app.payload.request.FavoriteQueryParam;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class FavoriteSpecification {
    public Specification<Favorite> hasTourIDLike(Integer tourId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("tourId"), tourId);
    }
    public Specification<Favorite> hasNameLikes(Integer keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), keyword);
    }


    public Specification<Favorite> getFavoriteSpecitification(FavoriteQueryParam favoriteQueryParam) {
        Specification<Favorite> spec = Specification.where(null);
        if (favoriteQueryParam.getTourId() != null) {
            spec = spec.and(hasTourIDLike(favoriteQueryParam.getTourId()));
        }
        if (favoriteQueryParam.getId() != null) {
            spec = spec.and(hasNameLikes(favoriteQueryParam.getId()));
        }
        return spec;
    }
}
