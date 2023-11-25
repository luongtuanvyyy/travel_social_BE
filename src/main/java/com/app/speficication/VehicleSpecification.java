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
            String nameWithoutDiacritics = removeDiacritics(name);
            String nameUpperCase = nameWithoutDiacritics.toUpperCase();
            Predicate likePredicate = criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("name")),
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

        return spec;
    }
}
