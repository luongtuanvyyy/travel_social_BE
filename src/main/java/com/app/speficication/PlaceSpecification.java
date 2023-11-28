package com.app.speficication;

import com.app.entity.Place;
import com.app.payload.request.PlaceQueryParam;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.text.Normalizer;

@Component
public class PlaceSpecification {
    public Specification<Place> hasIdEqual(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public Specification<Place> hasNamesLike(String Name) {
        return (Root<Place> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String nameWithoutDiacritics = removeDiacritics(Name.trim());
            String nameUpperCase = nameWithoutDiacritics.toUpperCase();
            Predicate likePredicate = criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("name")),
                    "%" + nameUpperCase + "%"
            );
            return likePredicate;
        };
    }
    public Specification<Place> hasAddressLike(String Address) {
        return (Root<Place> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String nameWithoutDiacritics = removeDiacritics(Address.trim());
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

    public Specification<Place> getPlaceSpecification(PlaceQueryParam placeQueryParam) {
        Specification<Place> spec = Specification.where(null);
        if (placeQueryParam.getId() != null) {
            spec = spec.and(hasIdEqual(placeQueryParam.getId()));
        }
        if (placeQueryParam.getName() != null) {
            spec = spec.and(hasNamesLike(placeQueryParam.getName()));
        }
        if (placeQueryParam.getAddress() != null) {
            spec = spec.and(hasAddressLike(placeQueryParam.getAddress()));
        }
        return spec;
    }
}
