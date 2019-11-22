package com.lxc.service;

import com.lxc.constants.OrderStatusEnum;
import com.lxc.entity.Cart;
import com.lxc.entity.Order;
import com.lxc.entity.User;
import org.springframework.data.domain.Page;

public interface OrderService {

    /**
     * retrieve orders by userId
     *
     * @param userId
     * @param pageNum
     * @return
     */
    Page<Order> findByUserId(Integer userId, int pageNum);

    /**
     * set order's status
     *
     * @param status
     * @param orderId
     */
    void setStatus(OrderStatusEnum status, Integer orderId);

    /**
     * load orderItems from cart
     * then complete all the other necessary information
     *
     * @param user
     * @param cart
     * @param order
     */
    void loadOrderItemAndComplete(User user, Cart cart, Order order);

    /**
     * pay order by orderId
     *
     * @param orderId
     */
    void pay(Integer orderId);

    /**
     * cancel order by orderId
     *
     * @param orderId
     */
    void cancel(Integer orderId);

    /**
     * save order
     *
     * @param order
     */
    void save(Order order);
}
