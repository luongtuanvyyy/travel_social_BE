package com.app.speficication;

import com.app.entity.Tour;
import com.app.payload.request.TourQueryParam;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Date;

@Component
public class TourSpecification {

    public Specification<Tour> hasIdEqual(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public Specification<Tour> hasVehicleLike(String vehicle) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("vehicle"), "%" + vehicle + "%");
    }


    public Specification<Tour> hasNameLike(String name) {
        return (Root<Tour> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String nameWithoutDiacritics = removeDiacritics(name);
            String nameUpperCase = nameWithoutDiacritics.toUpperCase();
            Predicate likePredicate = criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("name.trim()")),
                    "%" + nameUpperCase + "%"
            );
            return likePredicate;
        };
    }

    public Specification<Tour> hasAddressLike(String address) {
        return (Root<Tour> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String nameWithoutDiacritics = removeDiacritics(address);
            String nameUpperCase = nameWithoutDiacritics.toUpperCase();
            Predicate likePredicate = criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("address.trim()")),
                    "%" + nameUpperCase + "%"
            );
            return likePredicate;
        };
    }
    public static Specification<Tour> isNewlyPosted() {
        return (root, query, criteriaBuilder) -> {
            LocalDate today = LocalDate.now();
            return criteriaBuilder.equal(root.get("createdAt").as(LocalDate.class), today);
        };
    }

    private String removeDiacritics(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public Specification<Tour> priceGreaterThan(BigDecimal minPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public Specification<Tour> priceLessThan(BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public Specification<Tour> startDateGreaterThanOrEqualTo(Date startDate) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("start_date"), startDate);
        };
    }
    public static Specification<Tour> DateBetween(Date start_date, Date end_date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get("start_date"), start_date),
                criteriaBuilder.lessThanOrEqualTo(root.get("end_date"), end_date)
        );
    }
    public static Specification<Tour> bookingDateBetween(Date today, Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get("startDateBooking"), today),
                criteriaBuilder.lessThanOrEqualTo(root.get("endDateBooking"), today)
        );
    }

    public static Specification<Tour> bookingDateNotBetween(Date today) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.lessThan(root.get("startDateBooking"), today),
                criteriaBuilder.greaterThan(root.get("endDateBooking"), today)
        );
    }

    public Specification<Tour> sizeGreaterThan(Integer size) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("size"), size);
    }

    public Specification<Tour> getTourSpecification(TourQueryParam tourQueryParam) {
        Specification<Tour> spec = Specification.where(null);
        if (tourQueryParam.getId() != null) {
            spec = spec.and(hasIdEqual(tourQueryParam.getId()));
        }
        if (tourQueryParam.getName() != null) {
            spec = spec.and(hasNameLike(tourQueryParam.getName()));
        }
        if (tourQueryParam.getVehicle() != null) {
            spec = spec.and(hasVehicleLike(tourQueryParam.getVehicle()));
        }
        if (tourQueryParam.getMinPrice() != null) {
            spec = spec.and(priceGreaterThan(tourQueryParam.getMinPrice()));
        }
        if (tourQueryParam.getMaxPrice() != null) {
            spec = spec.and(priceLessThan(tourQueryParam.getMaxPrice()));
        }
        if (tourQueryParam.getStart_date() != null) {
            spec = spec.and(startDateGreaterThanOrEqualTo(tourQueryParam.getStart_date()));
        }
        if (tourQueryParam.getSize() != null) {
            spec = spec.and(sizeGreaterThan(tourQueryParam.getSize()));
        }
        if (tourQueryParam.getAddress() != null) {
            spec = spec.and(hasAddressLike(tourQueryParam.getAddress()));
        }
        if (tourQueryParam.getToday()!= null&& tourQueryParam.getId() != null) {
            spec = spec.and(bookingDateBetween(tourQueryParam.getToday(), tourQueryParam.getId()));
        }
        if (tourQueryParam.getToday() != null) {
            spec = spec.and(bookingDateNotBetween(tourQueryParam.getToday()));
        }
        if (tourQueryParam.getStart_date() != null && tourQueryParam.getEnd_date() != null) {
            spec = spec.and(DateBetween(tourQueryParam.getStart_date(), tourQueryParam.getEnd_date()));
        }
        return spec;
    }
}
