package com.app.repository;

import com.app.entity.Account;
import com.app.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByAccount(Account account);
}
