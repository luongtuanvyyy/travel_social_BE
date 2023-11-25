package com.app.speficication;

import com.app.entity.Booking;
import com.app.payload.request.BookingQueryParam;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BookingSpecification {
    public Specification<Booking>hasStatusLike(String status){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("STATUS"),"%"+status+"%") );
    }
    public Specification<Booking> getBookingSpecitification(BookingQueryParam bookingQueryParam) {
        Specification<Booking> spec = Specification.where(null);
        if (bookingQueryParam.getStatus() != null) {
            spec = spec.and(hasStatusLike(bookingQueryParam.getStatus()));
        }

        return spec;
    }
}
