package com.lxc.controller;

import com.lxc.constants.OrderStatus;
import com.lxc.entity.*;
import com.lxc.service.OrderService;
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
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setSuffix(".html");
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setViewResolvers(resolver).build();
    }

    @Test
    public void getOrders_happyPath() {

        User user = User.builder().id(1).build();

        assertThat(orderController.orders(mock(Model.class), 0, user)).isEqualTo("user/orders.html");

        verify(orderService).findByUserId(1, 0);
    }

    @Test
    public void toOrderInfo_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/order/orderInfo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/orderInfo.html"))
                .andReturn();
    }

    @Test
    public void completeOrderInfo_happyPath() {

        User user = mock(User.class);
        Cart cart = mock(Cart.class);
        Order order = mock(Order.class);

        assertThat(orderController.completeOrderInfo(user, cart, order)).isEqualTo("index");

        verify(orderService).completeOrderInfo(user, cart, order);
    }

    @Test
    public void pay_happyPath() {

        assertThat(orderController.pay(1, 0, mock(Model.class))).isEqualTo("forward:/order/orders");

        verify(orderService).pay(1);
    }

    @Test
    public void cancel_happyPath() {

        assertThat(orderController.cancel(1, 0, mock(Model.class))).isEqualTo("forward:/order/orders");

        verify(orderService).cancel(1);
    }

    @Test
    public void refund_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/order/refund")
                .param("orderId", String.valueOf(1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("forward:/order/orders"))
                .andReturn();
        verify(orderService).setStatus(OrderStatus.UNPAID, 1);
    }

    @Test
    public void recover_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/order/recover")
                .param("orderId", String.valueOf(1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("forward:/order/orders"))
                .andReturn();
        verify(orderService).setStatus(OrderStatus.UNPAID, 1);
    }
}
