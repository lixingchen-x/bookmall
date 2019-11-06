package com.lxc.service.impl;

import com.lxc.entity.Order;
import com.lxc.repository.OrderRepository;
import com.lxc.service.impl.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    public void findByUsername_happyPath() {

        Page mockedPage = mock(Page.class);
        when(orderRepository.findByUsername("abc", pageable)).thenReturn(mockedPage);
        assertThat(orderService.findByUsername("abc", PAGE_NUM), is(mockedPage));
    }

    @Test
    public void findByUsername_shouldBeNull_ifUsernameDoesNotExist() {

        when(orderRepository.findByUsername("abc", pageable)).thenReturn(null);
        assertThat(orderService.findByUsername("abc", PAGE_NUM), is(nullValue()));
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
        assertThat(orderRepository.getOne(1).getStatus(), equalTo("anyStatus"));
    }
}
