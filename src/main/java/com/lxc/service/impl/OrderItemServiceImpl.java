package com.lxc.service.impl;

import com.lxc.entity.OrderItem;
import com.lxc.repository.OrderItemRepository;
import com.lxc.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public void save(OrderItem orderItem) {

        orderItemRepository.save(orderItem);
    }
}
