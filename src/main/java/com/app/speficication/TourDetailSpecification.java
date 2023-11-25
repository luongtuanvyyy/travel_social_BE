package com.app.speficication;

import com.app.entity.TourDetail;
import com.app.payload.request.TourDetailQueryParam;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.time.LocalDate;

@Component
public class TourDetailSpecification {
    public Specification<TourDetail> hasIdEqual(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
    public Specification<TourDetail> hasVehicleLike(String vehicle) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("vehicle"),  "%"+vehicle+"%");
    }
    public Specification<TourDetail> hasPlaceLike(String place) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("place"),  "%"+place+"%");
    }
    public Specification<TourDetail> hasHotelLike(String hotel) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("hotel"),  "%"+hotel+"%");
    }
    public Specification<TourDetail> hasRestaurantLike(String restaurant) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("restaurant"),  "%"+restaurant+"%");
    }
    public Specification<TourDetail> hasTourLike(String tour) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("tour"),  "%"+tour+"%");
    }
    public Specification<TourDetail> hasNameLike(String name) {
        return (Root<TourDetail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
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


    public Specification<TourDetail> startDateGreaterThanOrEqualTo(LocalDate startDate) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("start_date"), startDate);
        };
    }

    public void sortRegistered(){

        return  ;
    }

    public Specification<TourDetail> getTourDetailSpecification(TourDetailQueryParam tourDetailQueryParam) {
        Specification<TourDetail> spec = Specification.where(null);
        if (tourDetailQueryParam.getId() != null) {
            spec = spec.and(hasIdEqual(tourDetailQueryParam.getId()));
        }
        if (tourDetailQueryParam.getVehicle() != null) {
            spec = spec.and(hasVehicleLike(tourDetailQueryParam.getVehicle()));
        }
        if (tourDetailQueryParam.getPlace() != null) {
            spec = spec.and(hasPlaceLike(tourDetailQueryParam.getPlace()));
        }
        if (tourDetailQueryParam.getHotel() != null) {
            spec = spec.and(hasHotelLike(tourDetailQueryParam.getHotel()));
        }
        if (tourDetailQueryParam.getRestaurant() != null) {
            spec = spec.and(hasRestaurantLike(tourDetailQueryParam.getRestaurant()));
        }
        if (tourDetailQueryParam.getTour() != null) {
            spec = spec.and(hasTourLike(tourDetailQueryParam.getTour()));
        }
        return spec;
    }
}