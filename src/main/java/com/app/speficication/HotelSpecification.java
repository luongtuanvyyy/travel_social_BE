package com.app.speficication;

import com.app.entity.Hotel;
import com.app.payload.request.HotelQueryParam;

import javax.persistence.criteria.*;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.text.Normalizer;

@Component
public class HotelSpecification {
    public Specification<Hotel> hasIdEqual(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
    public Specification<Hotel> hasRatingEqual(Float rating) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("rating"), rating);
    }
    public Specification<Hotel> hasNameLike(String name) {
        return (Root<Hotel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String nameWithoutDiacritics = removeDiacritics(name.trim());
            String nameUpperCase = nameWithoutDiacritics.toUpperCase();
            Predicate likePredicate = criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("name")),
                    "%" + nameUpperCase + "%"
            );
            return likePredicate;
        };
    }
    public Specification<Hotel> hasAddressLike(String address) {
        return (Root<Hotel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String nameWithoutDiacritics = removeDiacritics(address.trim());
            String nameUpperCase = nameWithoutDiacritics.toUpperCase();
            Predicate likePredicate = criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("address")),
                    "%" + nameUpperCase + "%"
            );
            return likePredicate;
        };
    }

    private String removeDiacritics(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public Specification<Hotel> getHotelSpecification(HotelQueryParam hotelQueryParam) {
        Specification<Hotel> spec = Specification.where(null);
        if (hotelQueryParam.getId() != null) {
            spec = spec.and(hasIdEqual(hotelQueryParam.getId()));
        }
        if (hotelQueryParam.getRating() != null) {
            spec = spec.and(hasRatingEqual(hotelQueryParam.getRating()));
        }
        if (hotelQueryParam.getName() != null) {
            spec = spec.and(hasNameLike(hotelQueryParam.getName()));
        }
        if (hotelQueryParam.getAddress() != null) {
            spec = spec.and(hasAddressLike(hotelQueryParam.getAddress()));
        }

        return spec;
    }
}
