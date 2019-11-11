package com.lxc.controller;

import com.lxc.constants.OrderStatus;
import com.lxc.entity.*;
import com.lxc.helper.AttributesHelper;
import com.lxc.service.CartService;
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

    @Mock
    private CartService cartService;

    @Mock
    private AttributesHelper attributesHelper;

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

        Cart cart = new Cart();
        Order order = createOrder(1);

        assertThat(orderController.completeOrderInfo(mock(User.class), cart, order)).isEqualTo("index");

        verify(orderService).save(order);
        verify(cartService).saveOrderItem(cart, 1);
        verify(attributesHelper).initCart();
    }

    @Test
    public void pay_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/order/pay")
                .param("orderId", String.valueOf(1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("forward:/order/orders"))
                .andReturn();
        verify(orderService).setStatus(OrderStatus.PAID, 1);
    }

    @Test
    public void cancel_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/order/cancel")
                .param("orderId", String.valueOf(1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("forward:/order/orders"))
                .andReturn();
        verify(orderService).setStatus(OrderStatus.CANCELLED, 1);
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

    private Order createOrder(Integer id) {

        Order order = new Order();
        order.setId(id);
        return order;
    }
}
