package com.lxc.controller;

import com.lxc.entity.Book;
import com.lxc.entity.Cart;
import com.lxc.entity.CartItem;
import com.lxc.helper.AttributesHelper;
import com.lxc.service.BookService;
import com.lxc.service.CartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CartController cartController;

    @Mock
    private BookService bookService;

    @Mock
    private CartService cartService;

    @Mock
    private AttributesHelper attributesHelper;

    @Before
    public void setUp() {

        this.mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void showCartItems_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/cart/cartItems"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/cart.html"))
                .andReturn();
    }

    @Test
    public void increaseQuantity_happyPath() {

        Cart cart = createCart(1, 1);

        assertThat(cartController.increase(1, cart)).isEqualTo("redirect:/cart/cartItems");

        assertThat(cart.getByBookId(1).getQuantity()).isEqualTo(2);
        verify(bookService).decreaseStock(1);
        verify(attributesHelper).updateCart(cart);
    }

    @Test
    public void decreaseQuantity_happyPath() {

        Cart cart = createCart(1, 1);

        assertThat(cartController.decrease(1, cart)).isEqualTo("redirect:/cart/cartItems");

        assertThat(cart.getByBookId(1).getQuantity()).isEqualTo(0);
        verify(bookService).increaseStock(1, 1);
        verify(attributesHelper).updateCart(cart);
    }

    @Test
    public void deleteCartItem_happyPath() {

        Cart cart = createCart(1, 1);

        assertThat(cartController.delete(1, cart)).isEqualTo("redirect:/cart/cartItems");

        assertThat(cart.getCartItems()).hasSize(0);
        verify(bookService).increaseStock(1, 1);
        verify(attributesHelper).updateCart(cart);
    }

    @Test
    public void reset_happyPath() {

        Cart cart = createCart(1, 1);

        assertThat(cartController.reset(cart)).isEqualTo("redirect:/cart/cartItems");

        assertThat(cart.getCartItems()).hasSize(0);
        verify(cartService).rollBackStockForCartReset(cart);
        verify(attributesHelper).updateCart(cart);
    }

    @Test
    public void confirm_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/cart/toBuy"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/book/books"))
                .andReturn();
    }

    private Cart createCart(Integer id, Integer quantity) {

        Book book = Book.builder().id(id).build();
        Cart cart = new Cart();
        cart.updateCart(CartItem.builder().book(book).quantity(quantity).build());
        return cart;
    }
}
