package com.app.speficication;

import com.app.entity.View;
import com.app.payload.request.ViewQueryParam;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ViewSpecification {

    public Specification<View> hasIdEqual(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public Specification<View> getViewSpecification(ViewQueryParam viewQueryParam) {
        Specification<View> spec = Specification.where(null);
        if (viewQueryParam.getId() != null) {
            spec = spec.and(hasIdEqual(viewQueryParam.getId()));
        }

        return spec;
    }
}
