package com.lxc.entity;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class OrderItemTest {

    @Test
    public void getBook_happyPath() {

        OrderItem item = new OrderItem();
        Book book = new Book();
        item.setBook(book);
        assertThat(item.getBook(), is(book));
    }

    @Test
    public void getQuantity_happyPath() {

        OrderItem item = new OrderItem();
        item.setQuantity(1);
        assertThat(item.getQuantity(), is(1));
    }

}
