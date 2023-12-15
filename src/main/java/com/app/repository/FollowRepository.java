package com.app.repository;

import com.app.dto.AccountData;
import com.app.entity.Follow;
import com.cloudinary.provisioning.Account;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    Integer countByAccountId(Integer accountId);

    Page<Follow> findAll(Specification<Follow> spec, Pageable pageable);


    @Query(value = "SELECT NEW com.app.dto.AccountData(a.id, a.name, a.avatar, a.isVerify) " +
            "FROM Follow f " +
            "JOIN Account a ON f.account.id = a.id " +
            "WHERE a.id NOT IN ( " +
            "  SELECT f.id " +
            "  FROM Follow f " +
            "  WHERE f.createdBy = :email " +
            ") " +
            "GROUP BY f.account.id " +
            "ORDER BY COUNT(DISTINCT f.createdBy ) DESC")
    Page<AccountData> findTopFollower( @Param("email") String email, Pageable pageable);
}
