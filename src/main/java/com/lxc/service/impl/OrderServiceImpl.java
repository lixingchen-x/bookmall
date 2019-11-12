package com.lxc.service.impl;

import com.lxc.constants.OrderStatus;
import com.lxc.entity.Cart;
import com.lxc.entity.Order;
import com.lxc.entity.OrderItem;
import com.lxc.entity.User;
import com.lxc.exception.StockNotEnoughException;
import com.lxc.helper.CartManager;
import com.lxc.repository.OrderItemRepository;
import com.lxc.repository.OrderRepository;
import com.lxc.service.BookService;
import com.lxc.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final int PAGE_SIZE = 10;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private CartManager cartManager;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public Page<Order> findByUserId(Integer userId, int pageNum) {

        Pageable pageable = PageRequest.of(pageNum, PAGE_SIZE, new Sort(Sort.Direction.ASC, "id"));
        return orderRepository.findByUserId(userId, pageable);
    }

    @Override
    public void setStatus(OrderStatus status, Integer orderId) {

        try {
            Order order = orderRepository.getOne(orderId);
            order.changeStatusTo(status);
            orderRepository.saveAndFlush(order);
        } catch (EntityNotFoundException e) {
            log.error("OrderId = {} does not exist!", orderId);
        }
    }

    @Override
    public void completeOrderInfo(User user, Cart cart, Order order) {

        order.setCreateDate(new Date());
        order.changeStatusTo(OrderStatus.UNPAID);
        order.setUserId(user.getId());

        getOrderItems(cart, order);
        save(order);
        saveOrderItems(order);

        cartManager.initCart();
    }

    @Override
    public void pay(Integer orderId) {

        setStatus(OrderStatus.PAID, orderId);
        decreaseStocks(orderRepository.getOne(orderId));
    }

    @Override
    public void cancel(Integer orderId) {

        setStatus(OrderStatus.CANCELLED, orderId);
        rollBackStocks(orderRepository.getOne(orderId));
    }

    private void save(Order order) {

        orderRepository.saveAndFlush(order);
    }

    private void rollBackStocks(Order order) {

        order.getOrderItems().forEach(orderItem ->
                bookService.increaseStock(orderItem.getBookId(), orderItem.getQuantity()));
    }

    private void getOrderItems(Cart cart, Order order) {

        cart.getCartItems().forEach(cartItem -> order.addOrderItem(cartItem.transferToOrderItem(order.getId())));
    }

    private void saveSingleOrderItem(OrderItem orderItem) {

        orderItemRepository.saveAndFlush(orderItem);
    }

    private void saveOrderItems(Order order) {

        order.getOrderItems().forEach(this::saveSingleOrderItem);
    }

    private void decreaseStocks(Order order) {

        order.getOrderItems().forEach(orderItem ->
                bookService.decreaseStock(orderItem.getBookId(), orderItem.getQuantity()));
    }
}
