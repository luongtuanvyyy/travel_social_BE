package com.app.speficication;

import com.app.entity.Restaurant;
import com.app.payload.request.RestaurantQueryParam;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.text.Normalizer;

@Component
public class RestaurantSpecification {
    public Specification<Restaurant> hasIdEqual(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
    public Specification<Restaurant> hasNameLike(String name) {
        return (Root<Restaurant> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String nameWithoutDiacritics = removeDiacritics(name);
            String nameUpperCase = nameWithoutDiacritics.toUpperCase();
            Predicate likePredicate = criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("name")),
                    "%" + nameUpperCase + "%"
            );
            return likePredicate;
        };
    }
    public Specification<Restaurant> hasAddressLike(String address) {
        return (Root<Restaurant> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String nameWithoutDiacritics = removeDiacritics(address);
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

    public Specification<Restaurant> getRestaurantSpecification(RestaurantQueryParam restaurantQueryParam) {
        Specification<Restaurant> spec = Specification.where(null);
        if (restaurantQueryParam.getId() != null) {
            spec = spec.and(hasIdEqual(restaurantQueryParam.getId()));
        }
        if (restaurantQueryParam.getAddress() != null) {
            spec = spec.and(hasAddressLike(restaurantQueryParam.getAddress()));
        }
        if (restaurantQueryParam.getName() != null) {
            spec = spec.and(hasNameLike(restaurantQueryParam.getName()));
        }

        return spec;
    }
}
