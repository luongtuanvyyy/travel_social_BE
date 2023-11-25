package com.app.speficication;

import com.app.entity.PersonBooking;
import com.app.payload.request.PersonBookingQueryParam;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PersonBookingSpecification {
    public Specification<PersonBooking> hasIdEqual(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public Specification<PersonBooking> getPersonBookingSpecification(PersonBookingQueryParam personBookingQueryParam) {
        Specification<PersonBooking> spec = Specification.where(null);
        if (personBookingQueryParam.getId() != null) {
            spec = spec.and(hasIdEqual(personBookingQueryParam.getId()));
        }

        return spec;
    }
}
