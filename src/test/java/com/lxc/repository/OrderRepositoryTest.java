package com.lxc.repository;

import com.lxc.entity.Order;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;

import static org.hamcrest.Matchers.contains;

public class OrderRepositoryTest extends Base {

    @Test
    public void findByUsernamePageable_happyPath() {

        Order order = insertTestOrder("abc");
        Page<Order> result = orderRepository.findByUsername("abc", pageable);
        Assert.assertThat(result.getContent(), contains(order));
        Assert.assertEquals(1, result.getContent().size());
    }

    @Test
    public void findByUsernamePageable_shouldBeZeroResult_ifOrderDoseNotExist() {

        insertTestOrder("abc");
        Page<Order> result = orderRepository.findByUsername("def", pageable);
        Assert.assertEquals(0, result.getContent().size());
    }

    private Order insertTestOrder(String username) {

        Order order = new Order(username);
        orderRepository.saveAndFlush(order);
        return order;
    }
}
