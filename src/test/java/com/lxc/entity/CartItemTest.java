package com.lxc.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

public class CartItemTest {

    @Test
    public void getBook_happyPath() {

        Book book = new Book();
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);

        assertThat(cartItem.getBook()).isEqualTo(book);
    }

    @Test
    public void getQuantity_happyPath() {

        CartItem cartItem = CartItem.builder().quantity(1).build();

        assertThat(cartItem.getQuantity()).isEqualTo(1);
    }

    @Test
    public void getSubTotal_happyPath() {

        Book book = Book.builder().price(100.0).build();
        CartItem cartItem = CartItem.builder().book(book).quantity(2).build();

        assertThat(cartItem.getSubTotal()).isEqualTo(200.0);
    }

    @Test
    public void increaseQuantity_happyPath() {

        CartItem cartItem = CartItem.builder().quantity(1).build();
        cartItem.increaseQuantity();

        assertThat(cartItem.getQuantity()).isEqualTo(2);
    }

    @Test
    public void decreaseQuantity_happyPath() {

        CartItem cartItem = CartItem.builder().quantity(1).build();
        cartItem.decreaseQuantity();

        assertThat(cartItem.getQuantity()).isEqualTo(0);
    }

    @Test
    public void decreaseQuantity_shouldBeZero_ifQuantityIsZero() {

        CartItem cartItem = CartItem.builder().quantity(0).build();
        cartItem.decreaseQuantity();

        assertThat(cartItem.getQuantity()).isEqualTo(0);
    }

    @Test
    public void toString_happyPath() {

        assertThat(CartItem.builder().toString()).isEqualTo("CartItem.CartItemBuilder(book=null, quantity=null)");
    }

    @Test
    public void transferToOrderItem_happyPath() {

        Book book = Book.builder().id(1).build();
        CartItem cartItem = CartItem.builder().book(book).quantity(1).build();

        assertThat(cartItem.transferToOrderItem(1).getBookId()).isEqualTo(1);
        assertThat(cartItem.transferToOrderItem(1).getQuantity()).isEqualTo(1);
        assertThat(cartItem.transferToOrderItem(1).getOrderId()).isEqualTo(1);
    }
}
