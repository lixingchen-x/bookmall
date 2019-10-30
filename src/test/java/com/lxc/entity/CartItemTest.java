package com.lxc.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CartItemTest {

    private CartItem cartItem = new CartItem();

    @Test
    public void getBook_happyPath() {

        Book book = new Book("a", 100.0);
        cartItem.setBook(book);
        assertEquals(book, cartItem.getBook());
    }

    @Test
    public void getQuantity_happyPath() {

        cartItem.setQuantity(1);
        assertEquals(1, (int) cartItem.getQuantity());
    }

    @Test
    public void getSubTotal_happyPath() {

        Book book = new Book("a", 100.0);
        cartItem.setBook(book);
        cartItem.setQuantity(2);
        assertEquals(200.0, cartItem.getSubTotal(), 0.0);
    }
}
