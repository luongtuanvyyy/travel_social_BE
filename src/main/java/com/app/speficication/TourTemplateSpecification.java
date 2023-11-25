package com.app.speficication;

import com.app.entity.TourTemplate;
import com.app.payload.request.TourTemplateQueryParam;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.text.Normalizer;

@Component
public class TourTemplateSpecification {
    public Specification<TourTemplate> hasNameTourTemplate(String name) {
        return (Root<TourTemplate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String nameWithoutDiacritics = removeDiacritics(name);
            String nameUpperCase = nameWithoutDiacritics.toUpperCase();
            Predicate likePredicate = criteriaBuilder.like(
                    criteriaBuilder.upper(root.get("name")),
                    "%" + nameUpperCase + "%");
            return likePredicate;
        };
    }

    private String removeDiacritics(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
    public Specification<TourTemplate> hasIdEqual(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
    public Specification<TourTemplate> getTourTemplateSpecification(TourTemplateQueryParam tourTemplateQueryParam) {
        Specification<TourTemplate> spec = Specification.where(null);
        if (tourTemplateQueryParam.getName() != null) {
            spec = spec.and(hasNameTourTemplate(tourTemplateQueryParam.getName()));
        }if (tourTemplateQueryParam.getId() != null) {
            spec = spec.and(hasIdEqual(tourTemplateQueryParam.getId()));
        }

        return spec;
    }
}
