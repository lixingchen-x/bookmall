package com.lxc.controller;

import com.lxc.entity.Cart;
import com.lxc.service.impl.BookServiceImpl;
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

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CartController cartController;

    @Mock
    private BookServiceImpl bookService;

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
    public void increaseQuantity_happyPath() throws Exception {

        Cart mockedCart = mock(Cart.class);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cart/increase/{bookId}", 1)
                .sessionAttr("cart", mockedCart))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/cart/cartItems"))
                .andReturn();
        verify(mockedCart).increaseQuantity(1);
        verify(bookService).decreaseStock(1);
    }

    @Test
    public void decreaseQuantity_happyPath() throws Exception {

        Cart mockedCart = mock(Cart.class);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cart/decrease/{bookId}", 1)
                .sessionAttr("cart", mockedCart))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/cart/cartItems"))
                .andReturn();
        verify(mockedCart).decreaseQuantity(1);
        verify(bookService).increaseStock(1);
    }

    @Test
    public void deleteCartItem_happyPath() throws Exception {

        Cart mockedCart = mock(Cart.class);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cart/delete/{bookId}", 1)
                .sessionAttr("cart", mockedCart))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/cart/cartItems"))
                .andReturn();
        verify(mockedCart).removeCartItem(1);
    }

    @Test
    public void reset_happyPath() throws Exception {

        Cart mockedCart = mock(Cart.class);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cart/reset")
                .sessionAttr("cart", mockedCart))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/cart/cartItems"))
                .andReturn();
        verify(mockedCart).resetCart();
    }

    @Test
    public void confirm_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/cart/toBuy"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/book/books"))
                .andReturn();
    }
}
