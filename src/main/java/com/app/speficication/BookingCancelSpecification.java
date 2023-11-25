package com.app.speficication;

import com.app.entity.BookingCancel;
import com.app.payload.request.BookingCancelQueryParam;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BookingCancelSpecification {
    public Specification<BookingCancel>hasIdEqual(Integer id){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"),id) );
    }
    public Specification<BookingCancel> getBookingCanceSpecitification(BookingCancelQueryParam bookingCancelQueryParam) {
        Specification<BookingCancel> spec = Specification.where(null);
        if (bookingCancelQueryParam.getId() != null) {
            spec = spec.and(hasIdEqual(bookingCancelQueryParam.getId()));
        }

        return spec;
    }
}
