package com.lxc.service.impl;

import com.lxc.entity.Book;
import com.lxc.entity.Cart;
import com.lxc.entity.CartItem;
import com.lxc.helper.CartManager;
import com.lxc.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceImplTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private BookService bookService;

    @Mock
    private CartManager cartManager;

    @Test
    public void increaseQuantity_happyPath() {

        Cart cart = createCart(1, 1);

        cartService.increaseQuantity(1, cart);

        assertThat(cart.getByBookId(1).getQuantity()).isEqualTo(2);
        verify(cartManager).updateCart(cart);
    }

    @Test
    public void decreaseQuantity_happyPath() {

        Cart cart = createCart(1, 1);

        cartService.decreaseQuantity(1, cart);

        assertThat(cart.getByBookId(1).getQuantity()).isEqualTo(0);
        verify(cartManager).updateCart(cart);
    }

    @Test
    public void decreaseQuantity_shouldBeZero_ifQuantityIsZero() {

        Cart cart = createCart(1, 0);

        cartService.decreaseQuantity(1, cart);

        assertThat(cart.getByBookId(1).getQuantity()).isEqualTo(0);
        verify(cartManager).updateCart(cart);
    }

    @Test
    public void deleteBook_happyPath() {

        Cart cart = createCart(1, 1);

        cartService.deleteBook(1, cart);

        assertThat(cart.getCartItems()).isEmpty();
        verify(cartManager).updateCart(cart);
    }

    @Test
    public void reset_happyPath() {

        Cart cart = createCart(1, 1);

        cartService.reset(cart);

        assertThat(cart.getCartItems()).isEmpty();
        verify(cartManager).updateCart(cart);
    }

    @Test
    public void createCartItem_happyPath() {

        Book book = createBook(1);
        when(bookService.findById(1)).thenReturn(book);
        CartItem cartItem = cartService.createCartItem(1, 1);

        assertThat(book.getId()).isEqualTo(1);
        assertThat(cartItem.getQuantity()).isEqualTo(1);
    }

    private Book createBook(Integer bookId) {

        return Book.builder().id(bookId).build();
    }

    private Cart createCart(Integer id, Integer quantity) {

        Book book = Book.builder().id(id).build();
        Cart cart = new Cart();
        cart.updateCart(CartItem.builder().book(book).quantity(quantity).build());
        return cart;
    }
}
