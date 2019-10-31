package com.lxc.entity;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;

public class CartItemTest {

    @Test
    public void getBook_happyPath() {

        CartItem cartItem = new CartItem();
        Book book = new Book("a", 100.0);
        cartItem.setBook(book);
        assertThat(cartItem.getBook(), is(book));
    }

    @Test
    public void getQuantity_happyPath() {

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        assertThat(cartItem.getQuantity(), is(1));
    }

    @Test
    public void getSubTotal_happyPath() {

        CartItem cartItem = new CartItem();
        Book book = new Book("a", 100.0);
        cartItem.setBook(book);
        cartItem.setQuantity(2);
        assertThat(cartItem.getSubTotal(), is(200.0));
    }
}
