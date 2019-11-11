package com.lxc.controller;

import com.lxc.entity.Cart;
import com.lxc.entity.CartItem;
import com.lxc.helper.AttributesHelper;
import com.lxc.service.BookService;
import com.lxc.service.CartItemService;
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
    private BookService bookService;

    @Mock
    private CartItemService cartItemService;

    @Mock
    private AttributesHelper attributesHelper;

    @Test
    public void toCart_happyPath() {

        assertThat(shoppingController.cart()).isEqualTo("/user/cart.html");
    }

    @Test
    public void addToCart_whenFindByALL_happyPath() {

        Cart cart = mock(Cart.class);
        Cart mockedCart = getMockedCart(cart);

        assertThat(shoppingController.addToCart(1, 0, "all", "a", mock(Model.class), cart))
                .isEqualTo("forward:/book/books");

        assert_addToCart_success(1, mockedCart);
    }

    @Test
    public void addToCart_whenFindByName_happyPath() {

        Cart cart = mock(Cart.class);
        Cart mockedCart = getMockedCart(cart);

        assertThat(shoppingController.addToCart(1, 0, "name", "a", mock(Model.class), cart))
                .isEqualTo("forward:/book/findByName");

        assert_addToCart_success(1, mockedCart);
    }

    @Test
    public void addToCart_whenFindByAuthor_happyPath() {

        Cart cart = mock(Cart.class);
        Cart mockedCart = getMockedCart(cart);

        assertThat(shoppingController.addToCart(1, 0, "author", "a", mock(Model.class), cart))
                .isEqualTo("forward:/book/findByAuthor");

        assert_addToCart_success(1, mockedCart);
    }

    @Test
    public void addToCart_whenFindByIsbn_happyPath() {

        Cart cart = mock(Cart.class);
        Cart mockedCart = getMockedCart(cart);

        assertThat(shoppingController.addToCart(1, 0, "isbn", "a", mock(Model.class), cart))
                .isEqualTo("forward:/book/findByIsbn");

        assert_addToCart_success(1, mockedCart);
    }

    @Test
    public void addToCart_whenFindByALLWithNoParameter_happyPath() {

        Cart cart = mock(Cart.class);
        Cart mockedCart = getMockedCart(cart);

        assertThat(shoppingController.addToCart(1, 0, "", "a", mock(Model.class), cart))
                .isEqualTo("forward:/book/books");

        assert_addToCart_success(1, mockedCart);
    }

    private Cart getMockedCart(Cart cart) {

        Cart mockedCart = mock(Cart.class);
        CartItem mockedCartItem = mock(CartItem.class);
        when(cart.updateCart(mockedCartItem)).thenReturn(mockedCart);
        when(cartItemService.createCartItem(1, 1)).thenReturn(mockedCartItem);
        return mockedCart;
    }

    private void assert_addToCart_success(Integer bookId, Cart cart) {

        verify(bookService).decreaseStock(bookId);
        attributesHelper.updateCart(cart);
    }
}
