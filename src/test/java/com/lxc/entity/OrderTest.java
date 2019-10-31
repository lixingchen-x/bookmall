package com.lxc.entity;

import com.lxc.testUtils.StringToDate;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class OrderTest {

    @Test
    public void getCreateDate_happyPath() throws ParseException {

        Order order = new Order();
        Date date = StringToDate.stringToDate("2019-10-29");
        order.setCreateDate(date);
        assertThat(order.getCreateDate(), is(date));
    }

    @Test
    public void getOrderItems_happyPath() {

        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(new Book(), 1));
        orderItems.add(new OrderItem(new Book(), 1));
        order.setOrderItems(orderItems);
        assertThat(order.getOrderItems(), is(orderItems));
    }
}
