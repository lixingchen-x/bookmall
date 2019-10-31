package com.lxc.repository;

import com.lxc.entity.Order;
import org.junit.Test;
import org.springframework.data.domain.Page;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class OrderRepositoryTest extends BaseRepositoryTes {

    @Test
    public void findByUsernamePageable_happyPath() {

        Order order = insertTestOrder("abc");
        Page<Order> result = orderRepository.findByUsername("abc", pageable);
        assertThat(result.getContent(), hasItem(order));
        assertThat(result.getContent().size(), is(1));
    }

    @Test
    public void findByUsernamePageable_shouldBeEmpty_ifOrderDoesNotExist() {

        insertTestOrder("abc");
        Page<Order> result = orderRepository.findByUsername("def", pageable);
        assertThat(result.getContent().size(), is(0));
    }

    private Order insertTestOrder(String username) {

        Order order = new Order(username);
        orderRepository.saveAndFlush(order);
        return order;
    }
}
