package com.lxc.repository;

import com.lxc.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    /**
     * retrieve orders by userId
     *
     * @param userId
     * @param pageable
     * @return
     */
    Page<Order> findByUserId (Integer userId, Pageable pageable);
}
