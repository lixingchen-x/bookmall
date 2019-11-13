package com.lxc.service.impl;

import com.lxc.constants.OrderStatus;
import com.lxc.entity.*;
import com.lxc.helper.CartManager;
import com.lxc.repository.OrderItemRepository;
import com.lxc.repository.OrderRepository;
import com.lxc.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    private final int PAGE_SIZE = 10;

    private final int PAGE_NUM = 0;

    private Pageable pageable = PageRequest.of(PAGE_NUM, PAGE_SIZE, new Sort(Sort.Direction.ASC, "id"));

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private CartManager cartManager;

    @Mock
    private BookService bookService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void findByUserId_happyPath() {

        Page mockedPage = mock(Page.class);
        when(orderRepository.findByUserId(1, pageable)).thenReturn(mockedPage);

        assertThat(orderService.findByUserId(1, PAGE_NUM)).isEqualTo(mockedPage);
    }

    @Test
    public void findByUserId_shouldReturnNull_ifUserIdDoesNotExist() {

        when(orderRepository.findByUserId(1, pageable)).thenReturn(null);

        assertThat(orderService.findByUserId(1, PAGE_NUM)).isNull();
    }

    @Test
    public void setStatus_happyPath() {

        Order order = new Order();
        when(orderRepository.getOne(1)).thenReturn(order);

        orderService.setStatus(OrderStatus.PAID, 1);

        assertThat(orderRepository.getOne(1).getStatus()).isEqualTo(OrderStatus.PAID);
    }

    @Test
    public void setStatus_shouldDoNothing_ifOrderDoesNotExist() {

        when(orderRepository.getOne(1)).thenThrow(EntityNotFoundException.class);

        orderService.setStatus(OrderStatus.PAID, 1);

        verify(orderRepository, never()).saveAndFlush(any());
    }

    @Test
    public void completeOrderInfo_happyPath() {

        Order order = mock(Order.class);
        Order completeOrder = createOrder(1, 1, 1);
        Cart cart = mock(Cart.class);
        when(order.loadOrderItemsFromCart(cart)).thenReturn(completeOrder);

        assertThat(orderService.completeOrderInfo(User.builder().id(1).build(), cart, order)).isEqualTo(completeOrder);

        verify(cartManager).initCart();
        assertThat(completeOrder.getStatus()).isEqualTo(OrderStatus.UNPAID);
        assertThat(completeOrder.getUserId()).isEqualTo(1);
    }

    @Test
    public void saveOrderInfo_happyPath() {

        Order order = new Order();
        OrderItem orderItem = mock(OrderItem.class);
        order.addOrderItem(orderItem);

        orderService.saveOrderInfo(order);

        verify(orderRepository).saveAndFlush(order);
        verify(orderItemRepository).saveAndFlush(orderItem);
    }

    @Test
    public void pay_happyPath() {

        Order order = createOrder(1, 1, 1);
        when(orderRepository.getOne(1)).thenReturn(order);

        orderService.pay(1);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.PAID);
        verify(bookService).decreaseStock(1, 1);
    }

    @Test
    public void cancel_happyPath() {

        Order order = createOrder(1, 1, 1);
        when(orderRepository.getOne(1)).thenReturn(order);

        orderService.cancel(1);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELLED);
        verify(bookService).increaseStock(1, 1);
    }

    private Order createOrder(Integer orderId, Integer bookId, Integer quantity) {

        Order order = new Order();
        order.setId(orderId);
        OrderItem orderItem = new OrderItem();
        orderItem.setBookId(bookId);
        orderItem.setQuantity(quantity);
        order.addOrderItem(orderItem);
        return order;
    }
}
