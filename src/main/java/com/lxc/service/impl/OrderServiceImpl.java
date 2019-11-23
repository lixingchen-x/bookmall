package com.lxc.service.impl;

import com.lxc.constants.OrderStatus;
import com.lxc.entity.Cart;
import com.lxc.entity.Order;
import com.lxc.entity.User;
import com.lxc.exception.StockNotEnoughException;
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

    @Override
    public Page<Order> findByUserId(Integer userId, int pageNum) {

        Pageable pageable = PageRequest.of(pageNum, PAGE_SIZE, new Sort(Sort.Direction.ASC, "id"));
        return orderRepository.findByUserId(userId, pageable);
    }

    @Override
    public void setStatus(OrderStatus status, Integer orderId) {

        try {
            Order order = orderRepository.getOne(orderId);
            order.setStatus(status);
            orderRepository.saveAndFlush(order);
        } catch (EntityNotFoundException e) {
            log.error("OrderId = {} does not exist!", orderId);
        }
    }

    @Override
    public void completeOrder(User user, Cart cart, Order order) {

        order.loadOrderItemsFromCart(cart);
        order.setCreateDate(new Date());
        order.setStatus(OrderStatus.UNPAID);
        order.setUserId(user.getId());
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

    @Override
    public void save(Order order) {

        orderRepository.saveAndFlush(order);
    }

    private void rollBackStocks(Order order) {

        order.getOrderItems().forEach(orderItem ->
                bookService.increaseStock(orderItem.getBookId(), orderItem.getQuantity()));
    }

    private void decreaseStocks(Order order) {

        order.getOrderItems().forEach(orderItem ->
        {
            try {
                bookService.decreaseStock(orderItem.getBookId(), orderItem.getQuantity());
            } catch (StockNotEnoughException e) {
                log.error("BookId = {} does not have enough stock!", orderItem.getBookId());
            }
        });
    }
}
