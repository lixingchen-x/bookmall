package com.lxc.repository;

import com.lxc.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findByUsername (String username, Pageable pageable);

    List<Order> findByCreateDate(Date createDate);

    List<Order> findByUsername(String username);

    List<Order> findByPhoneNumber(String phoneNumber);
}
