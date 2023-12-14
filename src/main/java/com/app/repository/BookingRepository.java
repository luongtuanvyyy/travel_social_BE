package com.app.repository;

import com.app.entity.Booking;
import com.app.type.EBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Page<Booking> findAll(Specification<Booking> spec, Pageable pageable);

    @Query("SELECT b FROM Booking b WHERE b.status = :status")
    Page<Booking> getBookingByStatus(@Param("status") EBooking status, Pageable pageable);
}

