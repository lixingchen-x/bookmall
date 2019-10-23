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

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final int PAGE_SIZE=5;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Page<Order> findAllByPage(int pageNum) {
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        Pageable pageable = PageRequest.of(pageNum, PAGE_SIZE, sort);
        return orderRepository.findAll(pageable);
    }

    @Override
    public List<Order> findByCreateDate(Date createDate) {

        return orderRepository.findByCreateDate(createDate);
    }

    @Override
    public List<Order> findByUsername(String username) {

        return orderRepository.findByUsername(username);
    }

    @Override
    public List<Order> findByPhoneNumber(String phoneNumber) {

        return orderRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public void save(Order order) {

        orderRepository.saveAndFlush(order);
    }

    @Override
    public void deleteById(Integer id) {

        orderRepository.deleteById(id);
    }

    @Override
    public void pay(Integer id) {

        Order order = orderRepository.getOne(id);
        order.setStatus("paid");
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void cancel(Integer id) {

        Order order = orderRepository.getOne(id);
        order.setStatus("cancelled");
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void refund(Integer id) {

        Order order = orderRepository.getOne(id);
        order.setStatus("unpaid");
        orderRepository.saveAndFlush(order);
    }

    @Override
    public void recover(Integer id) {

        Order order = orderRepository.getOne(id);
        order.setStatus("unpaid");
        orderRepository.saveAndFlush(order);
    }
}
