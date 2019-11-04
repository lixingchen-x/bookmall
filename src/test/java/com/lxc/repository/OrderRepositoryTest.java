package com.lxc.repository;

import com.lxc.entity.Order;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.*;

public class OrderRepositoryTest extends BaseRepositoryTest {

    @Autowired
    protected OrderRepository orderRepository;

    @Test
    public void findByUsernamePageable_happyPath() {

        Order order = insertTestOrder("abc");
        Page<Order> result = orderRepository.findByUsername("abc", pageable);
        assertThat(result.getContent()).hasSize(1).contains(order);
    }

    @Test
    public void findByUsernamePageable_shouldBeEmpty_ifOrderDoesNotExist() {

        insertTestOrder("abc");
        Page<Order> result = orderRepository.findByUsername("def", pageable);
        assertThat(result.getContent()).isEmpty();
    }

    private Order insertTestOrder(String username) {

        Order order = new Order(username);
        orderRepository.saveAndFlush(order);
        return order;
    }
}
