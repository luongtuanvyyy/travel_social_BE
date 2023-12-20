package com.app.repository;

import com.app.dto.AccountData;
import com.app.entity.Account;
import com.app.entity.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    Integer countByAccountId(Integer accountId);

    Page<Follow> findAll(Specification<Follow> spec, Pageable pageable);


    @Query("SELECT new com.app.dto.AccountData(a.id, a.name, a.avatar, a.isVerify, a.email, COUNT(f)) " +
            "FROM Account a " +
            "JOIN Follow f ON f.account.id = a.id " +
            "GROUP BY a.id, a.name, a.avatar, a.isVerify, a.email ")
    List<AccountData> getAccountsOrderByFollowerCount();

    Follow findByAccountAndCreatedBy(com.app.entity.Account account, String createdBy);

    List<Follow> findByCreatedBy(String createdBy);

    List<Follow> findByAccount(Account account);
}
