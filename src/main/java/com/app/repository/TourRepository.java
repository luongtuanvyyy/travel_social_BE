package com.app.repository;

import com.app.dto.AccountData;
import com.app.dto.TourDto;
import com.app.entity.Account;
import com.app.entity.Tour;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TourRepository extends JpaRepository<Tour, Integer> {
    Page<Tour> findAll(Specification<Tour> spec, Pageable pageable);
    @Query("SELECT t FROM Tour t WHERE t.discount IS NOT NULL")
    Page<Tour> DiscountIsNotNull(Specification<Tour> spec, Pageable pageable);

    Optional<Tour> findTourById(Integer id);
    @Query(value = "SELECT new com.app.dto.AccountData(a.id, a.name, a.avatar, a.isVerify, a.email)\n" +
            "FROM Account a\n" +
            "WHERE a.email IN (\n" +
            "  SELECT t.createdBy\n" +
            "  FROM Tour t\n" +
            "  WHERE t.id = :tourId\n" +
            ")")
    Page<AccountData> getCompanyCreatedBY(@Param("tourId")Integer tourId, Pageable pageable);

    @Query("SELECT new com.app.dto.TourDto(" +
            "t.name, t.departure, t.image, t.size, t.registered, t.vehicle, t.adult, t.children, " +
            "t.baby, t.discount, t.cloudinaryId, t.startDateBooking, t.endDateBooking, " +
            "t.startDate, t.endDate,  a.name, a.avatar,a.email, r.comment, r.rating) " +
            "FROM Tour t " +
            "JOIN TourDetail td ON t.id = td.tour.id " +
            "JOIN Review r ON t.id = r.tourId.id " +
            "JOIN Account a ON r.createdBy = a.email " +
            "WHERE t.id = :tourId")
    Page<TourDto> findTourDetailById(@Param("tourId") Integer tourId, Pageable pageable);

}



