package com.lxc.controller;

import com.lxc.service.impl.OrderItemServiceImpl;
import com.lxc.service.impl.OrderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderServiceImpl orderService;

    @Mock
    private OrderItemServiceImpl orderItemService;

    @Before
    public void setUp() {

        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getOrders() throws Exception {

        Page mockedPage = mock(Page.class);
        when(orderService.findByUsername("abc", 0)).thenReturn(mockedPage);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/order/orders")
                .param("page", String.valueOf(0)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/orders.html"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderPage", mockedPage))
                .andReturn();
        //ToDo
    }
}
