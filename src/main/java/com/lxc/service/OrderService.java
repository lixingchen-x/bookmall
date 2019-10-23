package com.lxc.service;

import com.lxc.entity.Order;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface OrderService {

    Page<Order> findAllByPage(int pageNum);

    List<Order> findByCreateDate(Date createDate);

    List<Order> findByUsername(String username);

    List<Order> findByPhoneNumber(String phoneNumber);

    void save(Order order);

    void deleteById(Integer id);

    void pay(Integer id);

    void cancel(Integer id);

    void refund(Integer id);

    void recover(Integer id);
}
