package com.lxc.service;

import com.lxc.entity.Order;
import org.springframework.data.domain.Page;
import java.util.Date;
import java.util.List;

public interface OrderService {

    Page<Order> findByUsername (String username, int pageNum);

    Page<Order> findAllByPage(int pageNum);

    List<Order> findByCreateDate(Date createDate);

    List<Order> findByUsername(String username);

    List<Order> findByPhoneNumber(String phoneNumber);

    void save(Order order);

    void deleteById(Integer id);

    void setStatus(String status, Integer id);


}
