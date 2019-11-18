package com.lxc.entity;

import com.lxc.constants.OrderStatusEnum;
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

        order.setStatus(OrderStatusEnum.PAID);

        assertThat(order.getStatus()).isEqualTo(OrderStatusEnum.PAID);
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

        Cart cart = mock(Cart.class);
        CartItem cartItem = getCartItem(cart);
        OrderItem orderItem = getOrderItem(cartItem);
        Order order = getOrder(orderItem);

        order.loadOrderItemsFromCart(cart);

        assertThat(orderItem.getBookId()).isEqualTo(cartItem.getBookId());
        assertThat(orderItem.getQuantity()).isEqualTo(cartItem.getQuantity());
    }

    private Order createOrder(Integer orderId) {

        Order order = new Order();
        order.setId(orderId);
        return order;
    }

    private CartItem getCartItem(Cart cart) {

        Book book = Book.builder().id(1).build();
        CartItem cartItem = CartItem.builder().book(book).quantity(1).build();
        when(cart.getByBookId(1)).thenReturn(null);
        cart.updateCart(cartItem);
        when(cart.getCartItems()).thenReturn(List.of(cartItem));
        return cartItem;
    }

    private Order getOrder(OrderItem orderItem) {

        Order order = createOrder(1);
        order.addOrderItem(orderItem);
        return order;
    }

    private OrderItem getOrderItem(CartItem cartItem) {

        return cartItem.transferToOrderItem(1);
    }
}
