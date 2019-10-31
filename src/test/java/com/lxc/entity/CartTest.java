package com.lxc.entity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

public class CartTest {

    @Test
    public void getTotalPrice_happyPath() {

        Cart cart = new Cart();
        cart.addCartItem(new CartItem(createBook(1,100.0), 1));
        assertThat(cart.getTotalPrice(), is(100.0));
    }

    @Test
    public void getTotalPrice_shouldBeZero_ifCartItemsIsEmpty() {

        Cart cart = new Cart();
        assertThat(cart.getTotalPrice(), is(0.0));
    }

    @Test
    public void getTotalPrice_shouldBeCorrect_ifCartItemsSizeIsBiggerThanOne() {

        Cart cart = new Cart();
        cart.addCartItem(new CartItem(createBook(1,100.0), 1));
        cart.addCartItem(new CartItem(createBook(2,100.0), 1));
        assertThat(cart.getTotalPrice(), is(100.0 + 100.0));
    }

    @Test
    public void resetCart_happyPath() {

        Cart cart = new Cart();
        cart.addCartItem(new CartItem(createBook(1)));
        cart.resetCart();
        assertThat(cart.getCartItems().size(), is(0));
    }

    @Test
    public void getByBookId_happyPath() {

        Cart cart = createCart(1);
        assertThat(cart.getByBookId(1).getBook().getId(), is(1));
    }

    @Test
    public void getByBookId_shouldBeNull_ifBookIdDoesNotExist() {

        Cart cart = createCart(1);
        assertNull(cart.getByBookId(2));
    }

    @Test
    public void getByBookId_shouldBeNull_ifCartItemsIsEmpty() {

        Cart cart = new Cart();
        assertNull(cart.getByBookId(1));
    }

    @Test
    public void removeCartItem_happyPath() {

        Cart cart = createCart(1);
        cart.removeCartItem(1);
        assertNull(cart.getByBookId(1));
    }

    @Test
    public void removeCartItem_shouldDoNothing_ifCartItemsNull() {

        Cart cart = new Cart();
        CartItem cartItem = new CartItem(createBook(1));
        List mockedList = mock(ArrayList.class);
        cart.setCartItems(mockedList);
        cart.removeCartItem(1);
        verify(mockedList, never()).remove(cartItem);
    }

    @Test
    public void decreaseQuantity_happyPath() {

        Cart cart = createCart(1, 1);
        cart.decreaseQuantity(1);
        assertThat(cart.getByBookId(1).getQuantity(), is(0));
    }

    @Test
    public void decreaseQuantity_shouldDoNothing_ifIdDoesNotExist() {

        Cart cart = new Cart();
        CartItem cartItem = mock(CartItem.class);
        cartItem.setBook(createBook(1));
        cart.decreaseQuantity(1);
        verify(cartItem, never()).decreaseQuantity();
    }

    @Test
    public void increaseQuantity_happyPath() {

        Cart cart = createCart(1, 1);
        cart.increaseQuantity(1);
        assertThat(cart.getByBookId(1).getQuantity(), is(2));
    }

    @Test
    public void increaseQuantity_shouldDoNothing_ifIdDoesNotExist() {

        Cart cart = new Cart();
        CartItem cartItem = mock(CartItem.class);
        cartItem.setBook(createBook(1));
        cart.increaseQuantity(1);
        verify(cartItem, never()).increaseQuantity();
    }

    @Test
    public void contains_happyPath() {

        Cart cart = createCart(1);
        assertTrue(cart.contains(1));
    }

    @Test
    public void contains_shouldBeFalse_ifBookIdDoesNotExist() {

        Cart cart = createCart(1);
        assertFalse(cart.contains(2));
    }

    @Test
    public void addCartItem_CartItemsSizeDoNotChange_ifBookExists() {

        Cart cart = createCart(1, 1);
        Book book = createBook(1);
        cart.addCartItem(new CartItem(book, 1));
        assertThat(cart.getCartItems().size(), is(1));
        assertThat(cart.getByBookId(1).getQuantity(), is(2));
    }

    @Test
    public void addCartItem_CartItemsSizeChange_ifBookDoesNotExist() {

        Cart cart = createCart(1, 1);
        Book book = createBook(2);
        cart.addCartItem(new CartItem(book, 1));
        assertThat(cart.getCartItems().size(), is(2));
        assertThat(cart.getByBookId(1).getQuantity(), is(1));
    }

    private Cart createCart(Integer id) {

        Cart cart = new Cart();
        Book book = createBook(id);
        cart.addCartItem(new CartItem(book));
        return cart;
    }

    private Cart createCart(Integer id, Integer quantity) {

        Cart cart = new Cart();
        Book book = createBook(id);
        cart.addCartItem(new CartItem(book, quantity));
        return cart;
    }

    private Book createBook(Integer id) {

        Book book = new Book();
        book.setId(id);
        return book;
    }

    private Book createBook(Integer id, Double price) {

        Book book = new Book();
        book.setId(id);
        book.setPrice(price);
        return book;
    }
}
