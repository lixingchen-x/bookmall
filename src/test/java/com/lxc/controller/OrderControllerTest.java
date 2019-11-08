package com.lxc.controller;

import com.lxc.entity.*;
import com.lxc.service.OrderItemService;
import com.lxc.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private OrderItemService orderItemService;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setSuffix(".html");
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setViewResolvers(resolver).build();
    }

    @Test
    public void getOrders_happyPath() throws Exception {

        Page mockedPage = mock(Page.class);
        User user = User.builder().id(1).build();
        when(orderService.findByUserId(1, 0)).thenReturn(mockedPage);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/order/orders")
                .param("page", String.valueOf(0))
                .sessionAttr("user", user))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/orders.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderPage", mockedPage))
                .andReturn();
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

        MockHttpSession mockedSession = mock(MockHttpSession.class);
        Cart cart = mockSessionAttributes(mockedSession);
        OrderItem mockedOrderItem = getMockedOrderItem(cart);
        Order order = createOrder(1);

        assertThat(orderController.completeOrderInfo(mockedSession, order)).isEqualTo("index");

        verify(orderService).save(order);
        verify(orderItemService).save(mockedOrderItem);
    }

    @Test
    public void pay_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/order/pay")
                .param("orderId", String.valueOf(1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("forward:/order/orders"))
                .andReturn();
        verify(orderService).setStatus("PAID", 1);
    }

    @Test
    public void cancel_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/order/cancel")
                .param("orderId", String.valueOf(1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("forward:/order/orders"))
                .andReturn();
        verify(orderService).setStatus("CANCELLED", 1);
    }

    @Test
    public void refund_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/order/refund")
                .param("orderId", String.valueOf(1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("forward:/order/orders"))
                .andReturn();
        verify(orderService).setStatus("UNPAID", 1);
    }

    @Test
    public void recover_happyPath() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/order/recover")
                .param("orderId", String.valueOf(1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("forward:/order/orders"))
                .andReturn();
        verify(orderService).setStatus("UNPAID", 1);
    }

    private Order createOrder(Integer id) {

        Order order = new Order();
        order.setId(id);
        return order;
    }

    private OrderItem getMockedOrderItem(Cart cart) {

        CartItem cartItem = mock(CartItem.class);
        cart.getCartItems().add(cartItem);
        OrderItem orderItem = mock(OrderItem.class);
        when(cartItem.transferToOrderItem(1)).thenReturn(orderItem);
        return orderItem;
    }

    private Cart mockSessionAttributes(MockHttpSession session) {

        Cart cart = new Cart();
        User user = User.builder().id(1).build();
        when(session.getAttribute("cart")).thenReturn(cart);
        when(session.getAttribute("user")).thenReturn(user);
        return cart;
    }
}
