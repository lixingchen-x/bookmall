package com.lxc.repository;

import com.lxc.entity.Order;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.*;

public class OrderRepositoryTest extends BaseEntityRepositoryTest {

    @Autowired
    protected OrderRepository orderRepository;

    @Test
    public void findByUserIdPageable_happyPath() {

        Order order = insertTestOrder(1);

        Page<Order> result = orderRepository.findByUserId(1, pageable);

        assertThat(result.getContent()).hasSize(1).contains(order);
    }

    @Test
    public void findByUserIdPageable_shouldBeEmpty_ifOrderDoesNotExist() {

        insertTestOrder(1);

        Page<Order> result = orderRepository.findByUserId(2, pageable);

        assertThat(result.getContent()).isEmpty();
    }

    private Order insertTestOrder(Integer userId) {

        Order order = new Order();
        order.setUserId(userId);
        orderRepository.saveAndFlush(order);
        return order;
    }
}
