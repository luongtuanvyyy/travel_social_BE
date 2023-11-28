package com.app.speficication;

import com.app.entity.Vehicle;
import com.app.payload.request.VehicleQueryParam;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.text.Normalizer;

@Component
public class VehicleSpecification {

    public Specification<Vehicle> hasIdEqual(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
    public Specification<Vehicle> hasNameLike(String name) {
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String nameWithoutDiacritics = removeDiacritics(name.trim());
            String nameUpperCase = nameWithoutDiacritics.toUpperCase();
            Predicate likePredicate = criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("name")),
                    "%" + nameUpperCase + "%"
            );
            return likePredicate;
        };
    }
    public Specification<Vehicle> hasAddressLike(String address) {
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String nameWithoutDiacritics = removeDiacritics(address.trim());
            String nameUpperCase = nameWithoutDiacritics.toUpperCase();
            Predicate likePredicate = criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("address")),
                    "%" + nameUpperCase + "%"
            );
            return likePredicate;
        };
    }
    public Specification<Vehicle> hasTypeLike(String type) {
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String nameWithoutDiacritics = removeDiacritics(type.trim());
            String nameUpperCase = nameWithoutDiacritics.toUpperCase();
            Predicate likePredicate = criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("type")),
                    "%" + nameUpperCase + "%"
            );
            return likePredicate;
        };
    }
    public Specification<Vehicle> hasWebsiteLike(String website) {
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String nameWithoutDiacritics = removeDiacritics(website.trim());
            String nameUpperCase = nameWithoutDiacritics.toUpperCase();
            Predicate likePredicate = criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("website")),
                    "%" + nameUpperCase + "%"
            );
            return likePredicate;
        };
    }
    public Specification<Vehicle> hasEmailLike(String email) {
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String nameWithoutDiacritics = removeDiacritics(email.trim());
            String nameUpperCase = nameWithoutDiacritics.toUpperCase();
            Predicate likePredicate = criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("email")),
                    "%" + nameUpperCase + "%"
            );
            return likePredicate;
        };
    }
    public Specification<Vehicle> hasHotlineLike(String hotline) {
        return (Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String nameWithoutDiacritics = removeDiacritics(hotline.trim());
            String nameUpperCase = nameWithoutDiacritics.toUpperCase();
            Predicate likePredicate = criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("hotline")),
                    "%" + nameUpperCase + "%"
            );
            return likePredicate;
        };
    }

    private String removeDiacritics(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public Specification<Vehicle> getVehicleSpecification(VehicleQueryParam vehicleQueryParam) {
        Specification<Vehicle> spec = Specification.where(null);
        if (vehicleQueryParam.getId() != null) {
            spec = spec.and(hasIdEqual(vehicleQueryParam.getId()));
        }
        if (vehicleQueryParam.getName() != null) {
            spec = spec.and(hasNameLike(vehicleQueryParam.getName()));
        }
        if (vehicleQueryParam.getAddress() != null) {
            spec = spec.and(hasAddressLike(vehicleQueryParam.getAddress()));
        }
        if (vehicleQueryParam.getType() != null) {
            spec = spec.and(hasTypeLike(vehicleQueryParam.getType()));
        }
        if (vehicleQueryParam.getWebsite() != null) {
            spec = spec.and(hasWebsiteLike(vehicleQueryParam.getWebsite()));
        }
        if (vehicleQueryParam.getEmail() != null) {
            spec = spec.and(hasEmailLike(vehicleQueryParam.getEmail()));
        }
        if (vehicleQueryParam.getHotline() != null) {
            spec = spec.and(hasHotlineLike(vehicleQueryParam.getHotline()));
        }

        return spec;
    }
}
