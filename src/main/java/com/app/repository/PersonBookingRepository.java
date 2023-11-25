package com.app.repository;
import com.app.entity.PersonBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonBookingRepository extends JpaRepository<PersonBooking, Integer> {

    Page<PersonBooking> findAll(Specification<PersonBooking> spec, Pageable pageable);
}
