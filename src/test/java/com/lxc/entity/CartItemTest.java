package com.lxc.entity;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;

public class CartItemTest {

    @Test
    public void getSubTotal_happyPath() {

        Book book = Book.builder().price(100.0).build();
        CartItem cartItem = CartItem.builder().book(book).quantity(2).build();
        assertThat(cartItem.getSubTotal(), is(200.0));
    }

    @Test
    public void increaseQuantity_happyPath() {

        CartItem cartItem = CartItem.builder().quantity(1).build();
        cartItem.increaseQuantity();
        assertThat(cartItem.getQuantity(), is(2));
    }

    @Test
    public void decreaseQuantity_happyPath() {

        CartItem cartItem = CartItem.builder().quantity(1).build();
        cartItem.decreaseQuantity();
        assertThat(cartItem.getQuantity(), is(0));
    }

    @Test
    public void decreaseQuantity_shouldBeZero_ifQuantityIsZero() {

        CartItem cartItem = CartItem.builder().quantity(0).build();
        cartItem.decreaseQuantity();
        assertThat(cartItem.getQuantity(), is(0));
    }
}
