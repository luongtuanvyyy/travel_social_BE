package com.app.repository;
import com.app.entity.BookingNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingNotificationRepository extends JpaRepository<BookingNotification, Integer> {

    Page<BookingNotification> findAll(Specification<BookingNotification> spec, Pageable pageable);
}
