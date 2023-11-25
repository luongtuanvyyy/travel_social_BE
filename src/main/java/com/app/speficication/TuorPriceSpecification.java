package com.app.speficication;

import com.app.entity.TourPrice;
import com.app.payload.request.TourPriceQueryParam;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component

public class TuorPriceSpecification {

    public Specification<TourPrice> hasIdEqual(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public Specification<TourPrice> getSpecification(TourPriceQueryParam tuorPriceQueryParam) {
        Specification<TourPrice> spec = Specification.where(null);
        if (tuorPriceQueryParam.getId() != null) {
            spec = spec.and(hasIdEqual(tuorPriceQueryParam.getId()));
        }

        return spec;
    }
}
