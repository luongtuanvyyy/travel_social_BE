package com.app.repository;

import com.app.dto.AccountData;
import com.app.entity.Account;
import com.app.entity.Blog;


import com.app.modal.AccountDetail;
import com.app.modal.Image;
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
    Optional<Account> findById(Integer id);
    Optional<Account> findByEmail(String email);
    @Query(value = "SELECT a from Account a where a.email like :email")
    Account findByGmail(@Param("email") String email);
    Page<Account> findAll(Specification<Account> spec, Pageable pageable);

//    @Query(value = "SELECT new com.app.dto.AccountData(a.id, a.name, a.avatar, a.isVerify)" +
//            "FROM Account a " +
//            "WHERE a.email IN ( " +
//            "  SELECT f.createdBy " +
//            "  FROM Follow f " +
//            "  WHERE f.account.id = :followerId " +
//            ")")
//    Page<AccountData> findByFollowerId(@Param("followerId") Integer followerId, Pageable pageable);

//    @Query("SELECT new com.app.dto.AccountData(a.id, a.name, a.avatar, a.isVerify)  FROM Account a INNER JOIN Follow f on f.account.id = a.id where f.createdBy = :Gmail")
//    Page<AccountData> findFollowByGmail(@Param("Gmail") String Gmail, Pageable pageable);
//

    @Query(value = "SELECT NEW com.app.modal.Image(b.id, b.cloudinaryId, b.createdAt) FROM Blog b WHERE b.createdBy = :gmail AND b.isActivated = true ORDER BY b.createdAt DESC")
    Page<Image> getImageByGmail(@Param("gmail") String gmail, Pageable pageable);

    @Query("SELECT NEW com.app.modal.AccountDetail" +
            "(a.avatar, a.name, a.email, " +
            "COUNT(DISTINCT b.id) ," +
            "COUNT(DISTINCT l.id) ," +
            "COUNT(DISTINCT f.id))" +
            "FROM Account a " +
            "LEFT JOIN Blog b ON a.email = b.createdBy " +
            "LEFT JOIN Like l ON a.id = l.account.id " +
            "LEFT JOIN Follow f ON a.id = f.account.id " +
            "WHERE a.id = :id " +
            "GROUP BY a.avatar, a.name, a.email")
    AccountDetail getAccount(@Param("id") Integer id);

}
