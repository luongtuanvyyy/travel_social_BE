package com.app.speficication;

import com.app.entity.TourCancel;
import com.app.payload.request.TourCancelQueryParam;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TourCancelSpecification {
    public Specification<TourCancel>hasIdEqual(Integer id){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"),id) );
    }
    public Specification<TourCancel> getTourCanceSpecitification(TourCancelQueryParam tourCancelQueryParam) {
        Specification<TourCancel> spec = Specification.where(null);
        if (tourCancelQueryParam.getId() != null) {
            spec = spec.and(hasIdEqual(tourCancelQueryParam.getId()));
        }

        return spec;
    }
}
