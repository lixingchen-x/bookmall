package com.lxc.controller;

import com.lxc.entity.*;
import com.lxc.service.impl.OrderItemServiceImpl;
import com.lxc.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderItemServiceImpl orderItemService;

    @RequestMapping("orders")
    public String orders(Model model, @RequestParam(defaultValue = "0") Integer page, HttpSession session) {

        Page<Order> orderPage = orderService.findByUsername(((User)session.getAttribute("user")).getUsername(), page);
        model.addAttribute("orderPage", orderPage);
        model.addAttribute("page", page);
        return "user/orders.html";
    }


    @GetMapping("orderInfo")
    public String toOrderInfo() {

        return "user/orderInfo.html";
    }

    @PostMapping("orderInfo")
    public String completeOrderInfo(HttpSession session, Order order) {

        order.setCreateDate(new Date());
        order.setStatus("UNPAID");
        orderService.save(order);
        saveOrderItem((Cart)session.getAttribute("cart"), order.getId());
        session.setAttribute("cart", new Cart((User) session.getAttribute("user")));
        return "index";
    }

    @RequestMapping("pay")
    public String pay(@RequestParam(value = "orderId") Integer id, @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("page", page);
        orderService.setStatus("PAID", id);
        return "forward:/order/orders";
    }

    @RequestMapping("cancel")
    public String cancel(@RequestParam(value = "orderId") Integer id, @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("page", page);
        orderService.setStatus("CANCELLED", id);
        return "forward:/order/orders";
    }

    @RequestMapping("refund")
    public String refund(@RequestParam(value = "orderId") Integer id, @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("page", page);
        orderService.setStatus("UNPAID", id);
        return "forward:/order/orders";
    }

    @RequestMapping("recover")
    public String recover(@RequestParam(value = "orderId") Integer id, @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("page", page);
        orderService.setStatus("UNPAID", id);
        return "forward:/order/orders";
    }

    private void saveOrderItem(Cart cart, Integer id) {

        cart.getCartItems().forEach(cartItem -> saveAsOrderItem(cartItem, id));
    }

    private void saveAsOrderItem(CartItem cartItem, Integer id) {

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(id);
        orderItem.setBookId(cartItem.getBook().getId());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItemService.save(orderItem);
    }
}
