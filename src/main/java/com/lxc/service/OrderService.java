package com.lxc.service;

import com.lxc.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {

    Page<Order> findByUsername (String username, int pageNum);

    void save(Order order);

    void setStatus(String status, Integer id);
}
