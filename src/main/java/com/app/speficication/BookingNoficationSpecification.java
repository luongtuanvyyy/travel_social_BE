package com.app.speficication;

import com.app.entity.BookingNotification;
import com.app.payload.request.BookingNotificationQueryParam;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.security.Timestamp;

@Component
public class BookingNoficationSpecification {

    public Specification<BookingNotification> createTime(Timestamp date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("date"), date);
    }
    public Specification<BookingNotification> hasNameLikes(Integer keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("id"), "%" + keyword + "%");
    }


    public Specification<BookingNotification> getBookingNotificationSpectication(BookingNotificationQueryParam bookingNotificationQueryParam) {
        Specification<BookingNotification> spec = Specification.where(null);
        if (bookingNotificationQueryParam.getId() != null) {
            spec = spec.and(createTime(bookingNotificationQueryParam.getCreate_time()));
        }
        if (bookingNotificationQueryParam.getId() != null) {
            spec = spec.and(hasNameLikes(bookingNotificationQueryParam.getId()));
        }
        return spec;
    }
}
