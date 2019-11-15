package com.lxc.service;

import com.lxc.constants.OrderStatus;
import com.lxc.entity.Cart;
import com.lxc.entity.Order;
import com.lxc.entity.User;
import org.springframework.data.domain.Page;

public interface OrderService {

    Page<Order> findByUserId(Integer userId, int pageNum);

    void setStatus(OrderStatus status, Integer orderId);

    void completeOrderInfo(User user, Cart cart, Order order);

    void pay(Integer orderId);

    void cancel(Integer orderId);

    void save(Order order);
}
