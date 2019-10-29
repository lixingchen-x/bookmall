package com.lxc.repository;

import com.lxc.entity.Order;
import com.lxc.testUtils.StringToDate;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.contains;

public class OrderRepositoryTest extends BaseRepositoryTest {

    private Order order = new Order("abc", "123");

    @Test
    public void findByUsernamePageable_happyPath() {

        insertTestOrder(order);
        Page<Order> result = orderRepository.findByUsername("abc", pageable);
        Assert.assertThat(result.getContent(), contains(order));
    }

    @Test
    public void findByCreateDate_happyPath() throws ParseException {

        Date createDate = StringToDate.stringToDate("2019-10-29");
        order.setCreateDate(createDate);
        insertTestOrder(order);
        List<Order> result = orderRepository.findByCreateDate(createDate);
        Assert.assertThat(result, contains(order));
    }

    @Test
    public void findByUsername_happyPath() {

        insertTestOrder(order);
        List<Order> result = orderRepository.findByUsername("abc");
        Assert.assertThat(result, contains(order));
    }

    @Test
    public void findByPhoneNumber_happyPath() {

        insertTestOrder(order);
        List<Order> result = orderRepository.findByPhoneNumber("123");
        Assert.assertThat(result, contains(order));
    }

    private void insertTestOrder(Order order) {

        orderRepository.saveAndFlush(order);
    }
}
