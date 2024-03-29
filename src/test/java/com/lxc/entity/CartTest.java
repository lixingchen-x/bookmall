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
import static org.assertj.core.api.Assertions.*;

public class CartTest {

    @Test
    public void constructor_happyPath() {

        User user = new User();
        Cart cart = new Cart(user);

        assertThat(cart.getUser()).isEqualTo(user);
    }

    @Test
    public void equals_shouldEqual_ifUserIsSame() {

        Cart cart1 = createCartWithUserId(1);
        Cart cart2 = createCartWithUserId(1);
        assertThat(cart1).isEqualTo(cart2);
    }

    @Test
    public void equals_shouldNotEqual_ifUserIsDifferent() {

        Cart cart1 = createCartWithUserId(1);
        Cart cart2 = createCartWithUserId(2);
        assertThat(cart1).isNotEqualTo(cart2);
    }

    @Test
    public void equals_shouldHaveReflexivity() {

        Cart cart = createCartWithUserId(1);

        assertThat(cart).isEqualTo(cart);
    }

    @Test
    public void equals_shouldBeFalse_ifOtherIsNull() {

        Cart cart = createCartWithUserId(1);
        Cart other = null;

        assertThat(cart).isNotEqualTo(other);
    }

    @Test
    public void hashCode_shouldEqual_whenComparedWithSameUser() {

        Cart cart1 = createCartWithUserId(1);
        Cart cart2 = createCartWithUserId(1);

        assertThat(cart1.hashCode()).isEqualTo(cart2.hashCode());
    }

    @Test
    public void hashCode_shouldNotEqual_whenComparedWithDifferentUser() {

        Cart cart1 = createCartWithUserId(1);
        Cart cart2 = createCartWithUserId(2);

        assertThat(cart1.hashCode()).isNotEqualTo(cart2.hashCode());
    }

    @Test
    public void getUser_happyPath() {

        User user = new User();
        Cart cart = new Cart();
        cart.setUser(user);

        assertThat(cart.getUser()).isEqualTo(user);
    }

    @Test
    public void getCartItems_happyPath() {

        Cart cart = new Cart();
        List mockedCartItems = mock(List.class);
        cart.setCartItems(mockedCartItems);

        assertThat(cart.getCartItems()).isEqualTo(mockedCartItems);
    }

    @Test
    public void getTotalPrice_happyPath() {

        Cart cart = new Cart();
        cart.updateCart(createCartItem(1, 100.0, 1));

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
        cart.updateCart(createCartItem(1, 100.0, 1));
        cart.updateCart(createCartItem(2, 100.0, 1));

        assertThat(cart.getTotalPrice(), is(200.0));
    }

    @Test
    public void resetCart_happyPath() {

        Cart cart = createCart(1);

        cart.resetCart();

        assertThat(cart.getCartItems()).isEmpty();
    }

    @Test
    public void getByBookId_happyPath() {

        Cart cart = createCart(1);

        assertThat(cart.getByBookId(1).getBookId(), is(1));
    }

    @Test
    public void getByBookId_shouldReturnNull_ifBookIdDoesNotExist() {

        Cart cart = createCart(1);

        assertNull(cart.getByBookId(2));
    }

    @Test
    public void getByBookId_shouldReturnNull_ifCartItemsIsEmpty() {

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
    public void removeCartItem_shouldDoNothing_ifCartItemDoesNotExist() {

        Cart cart = new Cart();
        CartItem cartItem = createCartItem(1);
        List mockedList = mock(ArrayList.class);
        cart.setCartItems(mockedList);

        cart.removeCartItem(1);

        verify(mockedList, never()).remove(cartItem);
    }

    @Test
    public void decreaseQuantity_happyPath() {

        Cart cart = createCart(1, 1);

        cart.decreaseQuantity(1);

        assertThat(cart.getQuantity(1), is(0));
    }

    @Test
    public void decreaseQuantity_shouldDoNothing_ifIdDoesNotExist() {

        Cart cart = createCart(1, 1);

        cart.decreaseQuantity(2);

        assertThat(cart.getQuantity(1), is(1));
    }

    @Test
    public void decreaseQuantity_shouldKeepZero_ifQuantityIsZero() {

        Cart cart = createCart(1, 0);

        cart.decreaseQuantity(1);

        assertThat(cart.getQuantity(1), is(0));
    }

    @Test
    public void increaseQuantity_happyPath() {

        Cart cart = createCart(1, 1);

        cart.increaseQuantity(1);

        assertThat(cart.getQuantity(1), is(2));
    }

    @Test
    public void increaseQuantity_shouldDoNothing_ifIdDoesNotExist() {

        Cart cart = createCart(1, 1);

        cart.increaseQuantity(2);

        assertThat(cart.getQuantity(1), is(1));
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
    public void updateCart_cartItemsSizeDoNotChange_ifBookExists() {

        Cart cart = createCart(1, 1);

        cart.updateCart(createCartItem(1, 1));

        assertThat(cart.getCartItems()).hasSize(1);
        assertThat(cart.getQuantity(1), is(2));
    }

    @Test
    public void updateCart_cartItemsSizeChange_ifBookDoesNotExist() {

        Cart cart = createCart(1, 1);

        cart.updateCart(createCartItem(2, 1));

        assertThat(cart.getCartItems()).hasSize(2);
        assertThat(cart.getQuantity(1), is(1));
    }

    private Cart createCartWithUserId(Integer id) {

        return new Cart(User.builder().id(id).build());
    }

    private Cart createCart(Integer id) {

        Cart cart = new Cart();
        cart.updateCart(createCartItem(id));
        return cart;
    }

    private Cart createCart(Integer id, Integer quantity) {

        Cart cart = new Cart();
        cart.updateCart(createCartItem(id, quantity));
        return cart;
    }

    private CartItem createCartItem(Integer id, Double price, Integer quantity) {

        Book book = Book.builder().id(id).price(price).build();
        return CartItem.builder().book(book).quantity(quantity).build();
    }

    private CartItem createCartItem(Integer id, Integer quantity) {

        Book book = Book.builder().id(id).build();
        return CartItem.builder().book(book).quantity(quantity).build();
    }

    private CartItem createCartItem(Integer id) {

        Book book = Book.builder().id(id).build();
        return CartItem.builder().book(book).build();
    }
}
