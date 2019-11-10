package com.lxc.controller;

import com.lxc.entity.Book;
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

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CartController cartController;

    @Mock
    private BookService bookService;

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
        verify(bookService).increaseStock(1, 1);
    }

    @Test
    public void deleteCartItem_happyPath() throws Exception {

        Cart cart = createCart(1, 1);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cart/delete/{bookId}", 1)
                .sessionAttr("cart", cart))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/cart/cartItems"))
                .andReturn();
        verify(bookService).increaseStock(1, 1);
    }

    @Test
    public void reset_happyPath() throws Exception {

        Cart cart = createCart(1, 1);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/cart/reset")
                .sessionAttr("cart", cart))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/cart/cartItems"))
                .andReturn();
        verify(bookService).increaseStock(1, 1);
    }

    @Test
    public void confirm_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/cart/toBuy"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/book/books"))
                .andReturn();
    }

<<<<<<< HEAD
=======
    @Test
    public void rollBackStockForCartReset_happyPath() {

        Cart cart = createCart(1, 1);
        cartController.rollBackStockForCartReset(cart);
        verify(bookService).increaseStock(1, 1);
    }

>>>>>>> aaf0422d94439d859bb47ee116a0f517071a213c
    private Cart createCart(Integer id, Integer quantity) {

        Book book = Book.builder().id(id).build();
        Cart cart = new Cart();
        cart.updateCart(CartItem.builder().book(book).quantity(quantity).build());
        return cart;
    }
}
