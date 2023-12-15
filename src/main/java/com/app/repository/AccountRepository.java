package com.app.repository;

import com.app.dto.AccountData;
import com.app.entity.Account;
import com.app.entity.Blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);


    Page<Account> findAll(Specification<Account> spec, Pageable pageable);

    @Query(value = "SELECT new com.app.dto.AccountData(a.id, a.name, a.avatar, a.isVerify)\n" +
            "FROM Account a\n" +
            "WHERE a.email IN (\n" +
            "  SELECT f.createdBy\n" +
            "  FROM Follow f\n" +
            "  WHERE f.account.id = :followerId\n" +
            ")")
    Page<AccountData> findByFollowerId(@Param("followerId") Integer followerId, Pageable pageable);

    @Query("SELECT new com.app.dto.AccountData(a.id, a.name, a.avatar, a.isVerify)  FROM Account a INNER JOIN Follow f on f.account.id = a.id where f.createdBy = :Gmail")
    Page<AccountData> findFollowByGmail(@Param("Gmail") String Gmail, Pageable pageable);

    // @Query("SELECT image FROM Blog WHERE email = :createBy")
    // Page<String[]> findImageByCreatedBy(@Param("createBy") String createBy);

}
