package com.lxc.service;

import com.lxc.constants.OrderStatus;
import com.lxc.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderService {

    Page<Order> findByUserId(Integer userId, int pageNum);

    void save(Order order);

    void setStatus(OrderStatus status, Integer id);
}
