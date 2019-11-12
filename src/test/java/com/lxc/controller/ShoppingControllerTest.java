package com.lxc.controller;

import com.lxc.entity.Cart;
import com.lxc.entity.CartItem;
import com.lxc.helper.CartManager;
import com.lxc.service.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ShoppingControllerTest {

    @InjectMocks
    private ShoppingController shoppingController;

    @Mock
    private CartService cartService;

    @Mock
    private CartManager cartManager;

    @Test
    public void toCart_happyPath() {

        assertThat(shoppingController.cart()).isEqualTo("/user/cart.html");
    }

    @Test
    public void addToCart_whenFindByALL_happyPath() {

        Cart mockedCart = getMockedCart();

        assertThat(shoppingController.addToCart(1, 0, "all", "a", mock(Model.class), mockedCart))
                .isEqualTo("forward:/book/books");

        verify(cartManager).updateCart(mockedCart);
    }

    @Test
    public void addToCart_whenFindByName_happyPath() {

        Cart mockedCart = getMockedCart();

        assertThat(shoppingController.addToCart(1, 0, "name", "a", mock(Model.class), mockedCart))
                .isEqualTo("forward:/book/findByName");

        verify(cartManager).updateCart(mockedCart);
    }

    @Test
    public void addToCart_whenFindByAuthor_happyPath() {

        Cart mockedCart = getMockedCart();

        assertThat(shoppingController.addToCart(1, 0, "author", "a", mock(Model.class), mockedCart))
                .isEqualTo("forward:/book/findByAuthor");

        verify(cartManager).updateCart(mockedCart);
    }

    @Test
    public void addToCart_whenFindByIsbn_happyPath() {

        Cart mockedCart = getMockedCart();

        assertThat(shoppingController.addToCart(1, 0, "isbn", "a", mock(Model.class), mockedCart))
                .isEqualTo("forward:/book/findByIsbn");

        verify(cartManager).updateCart(mockedCart);
    }

    @Test
    public void addToCart_whenFindByALLWithNoParameter_happyPath() {

        Cart mockedCart = getMockedCart();

        assertThat(shoppingController.addToCart(1, 0, "", "a", mock(Model.class), mockedCart))
                .isEqualTo("forward:/book/books");

        verify(cartManager).updateCart(mockedCart);
    }

    private Cart getMockedCart() {

        Cart mockedCart = mock(Cart.class);
        CartItem mockedCartItem = mock(CartItem.class);
        when(mockedCart.updateCart(mockedCartItem)).thenReturn(mockedCart);
        when(cartService.createCartItem(1, 1)).thenReturn(mockedCartItem);
        return mockedCart;
    }
}
