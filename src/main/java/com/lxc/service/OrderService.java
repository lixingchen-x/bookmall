package com.lxc.service;

import com.lxc.constants.OrderStatus;
import com.lxc.entity.Cart;
import com.lxc.entity.Order;
import com.lxc.entity.User;
import org.springframework.data.domain.Page;

public interface OrderService {

    Page<Order> findByUserId(Integer userId, int pageNum);

    void setStatus(OrderStatus status, Integer orderId);

    /**
     * To get a complete order there are three steps:
     * 1. get user's order form information
     * 2. load orderItems from cart
     * 3. complete all the other necessary information
     */
    void completeOrder(User user, Cart cart, Order order);

    void pay(Integer orderId);

    void cancel(Integer orderId);

    void save(Order order);
}
