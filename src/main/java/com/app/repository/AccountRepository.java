package com.app.repository;

import com.app.dto.AccountData;
import com.app.entity.Account;
import com.app.entity.Blog;

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
    Optional<Account> findByEmail(String email);


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

//    @Query(value = "SELECT NEW com.app.modal.Image(id " +
//            ", cloudinaryId" +
//            ", createdAt ) " +
//            "FROM Blog " +
//            "WHERE " +
//            "createdBy = 'tuanvy@gmail.com' " +
//            "AND isActivated = 1 " +
//            "ORDER BY " +
//            "createdAt DESC")
//    Page<Image> getImageByGmail(@Param("Gmail") String Gmail, Pageable pageable);
}
