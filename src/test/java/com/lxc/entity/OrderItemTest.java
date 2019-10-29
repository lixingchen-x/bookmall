package com.lxc.entity;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderItemTest {

    OrderItem item = new OrderItem();

    @Test
    public void getBook_happyPath() {

        Book book = new Book();
        item.setBook(book);
        assertEquals(book, item.getBook());
    }

    @Test
    public void getQuantity_happyPath() {

        item.setQuantity(1);
        assertTrue(item.getQuantity() == 1);
    }

}
