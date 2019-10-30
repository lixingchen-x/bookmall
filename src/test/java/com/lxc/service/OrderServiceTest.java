package com.lxc.service;

import com.lxc.entity.Order;
import com.lxc.repository.OrderRepository;
import com.lxc.service.impl.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    private final int PAGE_SIZE = 10;

    private final int PAGE_NUM = 0;

    private Pageable pageable = PageRequest.of(PAGE_NUM, PAGE_SIZE, new Sort(Sort.Direction.ASC, "id"));

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void findByUsername_happyPath() {

        orderService.findByUsername("abc", PAGE_NUM);
        verify(orderRepository).findByUsername("abc", pageable);
    }

    @Test
    public void findByUsername_shouldBeNull_ifUsernameDoseNotExist() {

        when(orderRepository.findByUsername("abc", pageable)).thenReturn(null);
        assertNull(orderService.findByUsername("abc", PAGE_NUM));
    }

    @Test
    public void save_happyPath() {

        Order order = new Order();
        orderService.save(order);
        verify(orderRepository).saveAndFlush(order);
    }

    @Test
    public void setStatus_happyPath() {

        Order order = new Order();
        when(orderRepository.getOne(1)).thenReturn(order);
        orderService.setStatus("anyStatus", 1);
        assertEquals("anyStatus", orderRepository.getOne(1).getStatus());
    }
}
