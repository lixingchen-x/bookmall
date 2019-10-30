package com.lxc.entity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CartTest {

    private Cart cart;

    @Test
    public void getTotalPrice_happyPath() {

        cart = new Cart();
        cart.setCartItems(List.of(new CartItem(new Book(null, 100.0), 1)));
        assertEquals(100.0, cart.getTotalPrice(), 0.0);
    }

    @Test
    public void getTotalPrice_shouldBeZero_ifCartItemsIsEmpty() {

        cart = new Cart();
        assertEquals(0.0, cart.getTotalPrice(), 0.0);
    }

    @Test
    public void getTotalPrice_shouldBeCorrect_ifCartItemsSizeIsBiggerThanOne() {

        cart = new Cart();
        cart.setCartItems(List.of(new CartItem(new Book(null, 100.0), 1),
                new CartItem(new Book(null, 100.0), 1)));
        assertEquals(100.0 + 100.0, cart.getTotalPrice(), 0.0);
    }

    @Test
    public void resetCart_happyPath() {

        cart = new Cart();
        cart.setCartItems(List.of(new CartItem()));
        cart.setCartItems(new ArrayList<>(cart.getCartItems())); //由Arrays的内部类ArrayList转为java.util.ArrayList
        cart.resetCart();
        assertEquals(0, cart.getCartItems().size());
    }

    @Test
    public void getByBookId_happyPath() {

        cart = createCartWithBookID(1);
        assertEquals(1, (int)cart.getByBookId(1).getBook().getId());
    }

    @Test
    public void getByBookId_shouldBeNull_ifBookIdDoesNotExist() {

        cart = createCartWithBookID(1);
        assertNull(cart.getByBookId(2));
    }

    @Test
    public void getByBookId_shouldBeNull_ifCartItemsIsEmpty() {

        cart = new Cart();
        assertNull(cart.getByBookId(1));
    }

    @Test
    public void removeCartItem_happyPath() {

        cart = createCartWithBookID(1);
        cart.setCartItems(new ArrayList<>(cart.getCartItems()));
        cart.removeCartItem(1);
        assertNull(cart.getByBookId(1));
    }

    @Test
    public void decreaseQuantity_happyPath() {

        cart = createCartWithBookIDAndQuantityOne(1, 1);
        cart.decreaseQuantity(1);
        assertEquals(0, (int)cart.getByBookId(1).getQuantity());
    }

    @Test
    public void increaseQuantity_happyPath() {

        cart = createCartWithBookIDAndQuantityOne(1, 1);
        cart.increaseQuantity(1);
        assertEquals(2, (int)cart.getByBookId(1).getQuantity());
    }

    @Test
    public void contains_happyPath() {

        cart = createCartWithBookID(1);
        assertTrue(cart.contains(1));
    }

    @Test
    public void contains_shouldBeFalse_ifBookIdDoesNotExist() {

        cart = createCartWithBookID(1);
        assertFalse(cart.contains(2));
    }

    private Cart createCartWithBookID(Integer id) {

        cart = new Cart();
        Book book = new Book();
        book.setId(id);
        cart.setCartItems(List.of(new CartItem(book)));
        return cart;
    }

    private Cart createCartWithBookIDAndQuantityOne(Integer id, Integer quantity) {

        cart = new Cart();
        Book book = new Book();
        book.setId(id);
        cart.setCartItems(List.of(new CartItem(book, quantity)));
        return cart;
    }

}
