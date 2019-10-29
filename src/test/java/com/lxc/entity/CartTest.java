package com.lxc.entity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CartTest {

    private static List<CartItem> cartItems;
    private static Cart cart = new Cart();

    static  {
        cartItems = new ArrayList<>();
        CartItem cartItem1 = new CartItem(new Book("a", 100.0), 1);
        CartItem cartItem2 = new CartItem(new Book("b", 100.0), 1);
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);
        cart.setCartItems(cartItems);
    }

    @Test
    public void getUser_happyPath() {

        User user = new User("abc", "123456");
        cart.setUser(user);
        assertSame(user, cart.getUser());
    }

    @Test
    public void getCartItems_happyPath() {

        assertSame(cartItems, cart.getCartItems());
    }

    @Test
    public void getTotalPrice_happyPath() {

        assertEquals(200.0, cart.getTotalPrice(), 0.0);
    }

    @Test
    public void add_happyPath() {

        CartItem cartItem = new CartItem(new Book("c", 100.0), 1);
        cart.add(cartItem);
        assertTrue(cart.getCartItems().contains(cartItem));
    }

    @Test
    public void resetCart_happyPath() {

        cart.resetCart();
        assertTrue(cart.getCartItems().size() == 0);
    }
}
