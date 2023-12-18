package com.app.repository;

import com.app.entity.BlogNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogNotificationRepository extends JpaRepository<BlogNotification, Integer> {
    Page<BlogNotification> findAll(Specification<BlogNotification> spec, Pageable pageable);

    @Query(value = "SELECT NEW com.app.modal.BlogNotification( b.id , bn.notificationType , a.name, a.avatar,  bn.createTime, bn.id ,a.isVerify) " +
            "FROM BlogNotification bn " +
            "JOIN Account a ON bn.account.id = a.id " +
            "JOIN Blog b ON bn.blog.id = b.id " +
            "WHERE b.createdBy = :email " +
            "ORDER BY bn.createTime DESC")
    Page<BlogNotification> findAllByEmail(@Param("email") String email, Pageable pageable);
}
