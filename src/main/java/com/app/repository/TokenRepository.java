package com.app.repository;

import com.app.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("select  t from Token t inner join  Account  a on t.account.id = a.id where  a.id = :accountId " +
            "and(t.expried = false or t.revoked = false ) ")
    List<Token> findAllValidTokensByAccount(Integer accountId);

    Optional<Token> findByToken(String Token);
}
