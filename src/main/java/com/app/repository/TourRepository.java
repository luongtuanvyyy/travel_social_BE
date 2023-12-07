package com.app.repository;

import com.app.dto.AccountData;
import com.app.entity.Tour;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Integer> {
    Page<Tour> findAll(Specification<Tour> spec, Pageable pageable);
    @Query("SELECT t FROM Tour t WHERE t.discount IS NOT NULL")
    Page<Tour> DiscountIsNotNull(Specification<Tour> spec, Pageable pageable);


    @Query(value = "SELECT new com.app.dto.AccountData(a.id, a.name, a.avatar, a.isVerify, a.email)\n" +
            "FROM Account a\n" +
            "WHERE a.email IN (\n" +
            "  SELECT t.createdBy\n" +
            "  FROM Tour t\n" +
            "  WHERE t.id = :tourId\n" +
            ")")
    Page<AccountData> getCompanyCreatedBY(@Param("tourId")Integer tourId, Pageable pageable);


}



