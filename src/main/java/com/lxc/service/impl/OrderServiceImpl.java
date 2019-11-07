package com.lxc.service.impl;

import com.lxc.entity.Order;
import com.lxc.repository.OrderRepository;
import com.lxc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class OrderServiceImpl implements OrderService {

    private final int PAGE_SIZE = 10;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Page<Order> findByUsername(String username, int pageNum) {

        Pageable pageable = PageRequest.of(pageNum, PAGE_SIZE, new Sort(Sort.Direction.ASC, "id"));
        return orderRepository.findByUsername(username, pageable);
    }

    @Override
    public void save(Order order) {

        orderRepository.saveAndFlush(order);
    }

    @Override
    public void setStatus(String status, Integer id) {

        try {
            Order order = orderRepository.getOne(id);
            order.setStatus(status);
            orderRepository.saveAndFlush(order);
        }catch (EntityNotFoundException e) {
            System.out.println("Order does not exist!");
        }
    }
}
