package com.lxc.entity;

import com.lxc.testUtils.StringToDate;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderTest {

    private Order order = new Order();

    @Test
    public void getAddress_happyPath() {

        order.setAddress("aaa");
        assertEquals("aaa", order.getAddress());
    }

    @Test
    public void getUsername_happyPath() {

        order.setUsername("aaa");
        assertEquals("aaa", order.getUsername());
    }

    @Test
    public void getStatus_happyPath() {

        order.setStatus("PAID");
        assertEquals("PAID", order.getStatus());
    }

    @Test
    public void getPhoneNumber_happyPath() {

        order.setPhoneNumber("15653374376");
        assertEquals("15653374376", order.getPhoneNumber());
    }

    @Test
    public void getCreateDate_happyPath() throws ParseException {

        Date date = StringToDate.stringToDate("2019-10-29");
        order.setCreateDate(date);
        assertEquals(date, order.getCreateDate());
    }

    @Test
    public void getOrderItems_happyPath() {

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(new Book(), 1));
        orderItems.add(new OrderItem(new Book(), 1));
        order.setOrderItems(orderItems);
        assertEquals(orderItems, order.getOrderItems());
    }
}
