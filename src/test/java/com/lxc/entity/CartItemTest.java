package com.lxc.entity;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;

public class CartItemTest {

    @Test
    public void getSubTotal_happyPath() {

        CartItem cartItem = new CartItem();
        Book book = Book.builder().price(100.0).build();
        cartItem.setBook(book);
        cartItem.setQuantity(2);
        assertThat(cartItem.getSubTotal(), is(200.0));
    }

    @Test
    public void increaseQuantity_happyPath() {

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.increaseQuantity();
        assertThat(cartItem.getQuantity(), is(2));
    }

    @Test
    public void decreaseQuantity_happyPath() {

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(1);
        cartItem.decreaseQuantity();
        assertThat(cartItem.getQuantity(), is(0));
    }

    @Test
    public void decreaseQuantity_shouldBeZero_ifQuantityIsZero() {

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(0);
        cartItem.decreaseQuantity();
        assertThat(cartItem.getQuantity(), is(0));
    }
}
