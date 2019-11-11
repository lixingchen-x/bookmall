package com.lxc.service.impl;

import com.lxc.entity.Book;
import com.lxc.entity.Cart;
import com.lxc.entity.CartItem;
import com.lxc.entity.OrderItem;
import com.lxc.service.BookService;
import com.lxc.service.OrderItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceImplTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private BookService bookService;

    @Mock
    private OrderItemService orderItemService;

    @Test
    public void rollBackStockForCartReset_happyPath() {

        Cart cart = createCart(1, 2);

        cartService.rollBackStockForCartReset(cart);

        verify(bookService).increaseStock(1, 2);
    }

    @Test
    public void saveOrderItem_happyPath() {

        Cart cart = createCart(1, 1);
        OrderItem mockedOrderItem = getMockedOrderItem(cart);

        cartService.saveOrderItem(cart, 1);

        verify(orderItemService).save(mockedOrderItem);
    }

    private Cart createCart(Integer id, Integer quantity) {

        Book book = Book.builder().id(id).build();
        Cart cart = new Cart();
        cart.updateCart(CartItem.builder().book(book).quantity(quantity).build());
        return cart;
    }

    private OrderItem getMockedOrderItem(Cart cart) {

        CartItem cartItem = mock(CartItem.class);
        cart.getCartItems().add(cartItem);
        OrderItem orderItem = mock(OrderItem.class);
        when(cartItem.transferToOrderItem(1)).thenReturn(orderItem);
        return orderItem;
    }
}
