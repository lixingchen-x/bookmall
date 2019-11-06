package com.lxc.controller;

import com.lxc.entity.Cart;
import com.lxc.entity.CartItem;
import com.lxc.service.BookService;
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
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ShoppingControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ShoppingController shoppingController;

    @Mock
    private BookService bookService;

    @Before
    public void setUp() {

        this.mockMvc = MockMvcBuilders.standaloneSetup(shoppingController).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void toCart_happyPath() {

        assertThat(shoppingController.cart()).isEqualTo("/user/cart.html");
    }

    @Test
    public void addToCart_happyPath() throws Exception {

        Cart mockedCart = mock(Cart.class);
        CartItem mockedCartItem = mock(CartItem.class);
        when(mockedCart.updateCart(mockedCartItem)).thenReturn(mockedCart);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/shopping/add")
                .param("bookId", String.valueOf(1))
                .param("page", String.valueOf(0))
                .sessionAttr("cart", mockedCart))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("forward:/book/find"))
                .andReturn();
        verify(bookService).decreaseStock(1);
    }

    @Test
    public void createCartItem_happyPath() {

        shoppingController.createCartItem(1, 1);
        verify(bookService).findById(1);
    }
}
