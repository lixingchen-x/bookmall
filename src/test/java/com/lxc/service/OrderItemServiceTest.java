package com.lxc.service;

import com.lxc.entity.OrderItem;
import com.lxc.repository.OrderItemRepository;
import com.lxc.service.impl.OrderItemServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @Test
    public void save_happyPath() {

        OrderItem orderItem = new OrderItem();
        orderItemService.save(orderItem);
        verify(orderItemRepository).save(orderItem);
    }
}
