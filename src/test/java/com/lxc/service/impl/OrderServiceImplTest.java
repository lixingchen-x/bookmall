package com.lxc.service.impl;

import com.lxc.constants.OrderStatus;
import com.lxc.entity.Order;
import com.lxc.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    private final int PAGE_SIZE = 10;

    private final int PAGE_NUM = 0;

    private Pageable pageable = PageRequest.of(PAGE_NUM, PAGE_SIZE, new Sort(Sort.Direction.ASC, "id"));

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void findByUserId_happyPath() {

        Page mockedPage = mock(Page.class);
        when(orderRepository.findByUserId(1, pageable)).thenReturn(mockedPage);

        assertThat(orderService.findByUserId(1, PAGE_NUM), is(mockedPage));
    }

    @Test
    public void findByUserId_shouldReturnNull_ifUserIdDoesNotExist() {

        when(orderRepository.findByUserId(1, pageable)).thenReturn(null);

        assertThat(orderService.findByUserId(1, PAGE_NUM), is(nullValue()));
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

        orderService.setStatus(OrderStatus.PAID, 1);

        assertThat(orderRepository.getOne(1).getStatus(), equalTo("PAID"));
    }

    @Test
    public void setStatus_shouldDoNothing_ifOrderDoesNotExist() {

        when(orderRepository.getOne(1)).thenThrow(EntityNotFoundException.class);

        orderService.setStatus(OrderStatus.PAID, 1);

        verify(orderRepository, never()).saveAndFlush(any());
    }
}
