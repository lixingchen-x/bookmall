package com.lxc.controller;

import com.lxc.constants.OrderStatus;
import com.lxc.entity.*;
import com.lxc.helper.AttributesHelper;
import com.lxc.helper.CurrentCart;
import com.lxc.helper.CurrentUser;
import com.lxc.service.CartService;
import com.lxc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private AttributesHelper attributesHelper;

    @RequestMapping("orders")
    public String orders(Model model, @RequestParam(defaultValue = "0") Integer page, @CurrentUser User user) {

        Page<Order> orderPage = orderService.findByUserId(user.getId(), page);
        model.addAttribute("orderPage", orderPage);
        model.addAttribute("page", page);
        return "user/orders.html";
    }

    @GetMapping("orderInfo")
    public String toOrderInfo() {

        return "user/orderInfo.html";
    }

    @PostMapping("orderInfo")
    public String completeOrderInfo(@CurrentUser User user, @CurrentCart Cart cart, Order order) {

        order.setCreateDate(new Date());
        order.changeStatusTo(OrderStatus.UNPAID);
        order.setUserId(user.getId());
        orderService.save(order);
        cartService.saveOrderItem(cart, order.getId());
        attributesHelper.initCart();
        return "index";
    }

    @RequestMapping("pay")
    public String pay(@RequestParam(value = "orderId") Integer id, @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("page", page);
        orderService.setStatus(OrderStatus.PAID, id);
        return "forward:/order/orders";
    }

    @RequestMapping("cancel")
    public String cancel(@RequestParam(value = "orderId") Integer id, @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("page", page);
        orderService.setStatus(OrderStatus.CANCELLED, id);
        return "forward:/order/orders";
    }

    @RequestMapping("refund")
    public String refund(@RequestParam(value = "orderId") Integer id, @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("page", page);
        orderService.setStatus(OrderStatus.UNPAID, id);
        return "forward:/order/orders";
    }

    @RequestMapping("recover")
    public String recover(@RequestParam(value = "orderId") Integer id, @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("page", page);
        orderService.setStatus(OrderStatus.UNPAID, id);
        return "forward:/order/orders";
    }
}
