package com.lxc.service.impl;

import com.lxc.entity.Book;
import com.lxc.entity.CartItem;
import com.lxc.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class CartItemServiceImplTest {

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    @Mock
    private BookService bookService;

    @Test
    public void createCartItem_happyPath() {

        Book book = createBook(1);
        when(bookService.findById(1)).thenReturn(book);
        CartItem cartItem = cartItemService.createCartItem(1, 1);

        assertThat(book.getId()).isEqualTo(1);
        assertThat(cartItem.getQuantity()).isEqualTo(1);
    }

    private Book createBook(Integer bookId) {

        return Book.builder().id(1).build();
    }
}
