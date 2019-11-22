package com.lxc.service.impl;

import com.lxc.constants.OrderStatusEnum;
import com.lxc.entity.*;
import com.lxc.exception.StockNotEnoughException;
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

        orderService.setStatus(OrderStatusEnum.PAID, 1);

        assertThat(orderRepository.getOne(1).getStatus()).isEqualTo(OrderStatusEnum.PAID);
    }

    @Test
    public void setStatus_shouldDoNothing_ifOrderDoesNotExist() {

        when(orderRepository.getOne(1)).thenThrow(EntityNotFoundException.class);

        orderService.setStatus(OrderStatusEnum.PAID, 1);

        verify(orderRepository, never()).saveAndFlush(any());
    }

    @Test
    public void loadOrderItemAndComplete_happyPath() {

        Order order = createOrder(1, 1, 1);

        orderService.loadOrderItemAndComplete(User.builder().id(1).build(), mock(Cart.class), order);

        assertThat(order.getStatus()).isEqualTo(OrderStatusEnum.UNPAID);
        assertThat(order.getUserId()).isEqualTo(1);
    }

    @Test
    public void save_happyPath() {

        Order order = new Order();

        orderService.save(order);

        verify(orderRepository).saveAndFlush(order);
    }

    @Test
    public void pay_happyPath() throws StockNotEnoughException {

        Order order = createOrder(1, 1, 1);
        when(orderRepository.getOne(1)).thenReturn(order);

        orderService.pay(1);

        assertThat(order.getStatus()).isEqualTo(OrderStatusEnum.PAID);
        verify(bookService).decreaseStock(1, 1);
    }

    @Test
    public void pay_shouldCatchException_ifSomeBookDoesNotHaveEnoughStock() throws StockNotEnoughException {

        Order order = createOrder(1, 1, 1);
        when(orderRepository.getOne(1)).thenReturn(order);
        doThrow(StockNotEnoughException.class).when(bookService).decreaseStock(1, 1);

        orderService.pay(1);
    }

    @Test
    public void cancel_happyPath() {

        Order order = createOrder(1, 1, 1);
        when(orderRepository.getOne(1)).thenReturn(order);

        orderService.cancel(1);

        assertThat(order.getStatus()).isEqualTo(OrderStatusEnum.CANCELLED);
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
