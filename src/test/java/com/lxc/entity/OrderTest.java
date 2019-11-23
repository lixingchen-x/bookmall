package com.lxc.entity;

import com.lxc.constants.OrderStatus;
import com.lxc.testUtils.DateUtils;
import org.junit.Test;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderTest {

    private Order order = new Order();

    @Test
    public void getId_happyPath() {

        order.setId(1);

        assertThat(order.getId()).isEqualTo(1);
    }

    @Test
    public void getCreateDate_happyPath() throws ParseException {

        Date date = DateUtils.parse("2019-10-29");
        order.setCreateDate(date);

        assertThat(order.getCreateDate()).isEqualTo(date);
    }

    @Test
    public void getAddress_happyPath() {

        order.setAddress("aaa");

        assertThat(order.getAddress()).isEqualTo("aaa");
    }

    @Test
    public void getUserId_happyPath() {

        order.setUserId(1);

        assertThat(order.getUserId()).isEqualTo(1);
    }

    @Test
    public void getReceiver_happyPath() {

        order.setReceiver("a");
        assertThat(order.getReceiver()).isEqualTo("a");
    }

    @Test
    public void getStatus_happyPath() {

        order.setStatus(OrderStatus.PAID);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.PAID);
    }

    @Test
    public void getPhoneNumber_happyPath() {

        order.setPhoneNumber("15653374376");

        assertThat(order.getPhoneNumber()).isEqualTo("15653374376");
    }

    @Test
    public void getOrderItems_happyPath() {

        List mockedOrderItems = mock(List.class);
        order.setOrderItems(mockedOrderItems);

        assertThat(order.getOrderItems()).isEqualTo(mockedOrderItems);
    }

    @Test
    public void loadOrderItemsFromCart_happyPath() {

        CartItem cartItem = mockCartItem(1);
        Cart cart = mockCart(cartItem);
        OrderItem expected = mockTransferToOrderItem(cartItem, 1);

        Order order = createOrder(1);
        order.loadOrderItemsFromCart(cart);

        assertThat(order.getOrderItems()).contains(expected);
    }

    private CartItem mockCartItem(Integer bookId) {

        CartItem cartItem = mock(CartItem.class);
        Book book = Book.builder().id(bookId).build();
        when(cartItem.getBook()).thenReturn(book);
        return cartItem;
    }

    private Cart mockCart(CartItem cartItem) {

        Cart cart = mock(Cart.class);
        when(cart.getCartItems()).thenReturn(List.of(cartItem));
        return cart;
    }

    private OrderItem mockTransferToOrderItem(CartItem cartItem, Integer orderId) {

        OrderItem orderItem = new OrderItem();
        when(cartItem.transferToOrderItem(orderId)).thenReturn(orderItem);
        return orderItem;
    }

    private Order createOrder(Integer orderId) {

        Order order = new Order();
        order.setId(orderId);
        return order;
    }
}
