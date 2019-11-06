package com.lxc.entity;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class OrderItemTest {

    private OrderItem item = new OrderItem();

    @Test
    public void getId_happyPath() {

        item.setId(1);
        assertThat(item.getId()).isEqualTo(1);
    }

    @Test
    public void getBookId_happyPath() {

        item.setBookId(1);
        assertThat(item.getBookId()).isEqualTo(1);
    }

    @Test
    public void getQuantity_happyPath() {

        item.setQuantity(1);
        assertThat(item.getQuantity()).isEqualTo(1);
    }

    @Test
    public void getOrderId_happyPath() {

        item.setOrderId(1);
        assertThat(item.getOrderId()).isEqualTo(1);
    }
}
