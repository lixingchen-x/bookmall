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
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderItemServiceImpl orderItemService;

    @RequestMapping("orders")
    public String orders(Model model, @RequestParam(defaultValue = "0") Integer page){

        // ToDo order要根据用户名来获取
        Page<Order> orderPages = orderService.findAllByPage(page);
        List<Order> orders=orderPages.getContent();
        model.addAttribute("orders", orders);
        model.addAttribute("totalPages", orderPages.getTotalPages());
        model.addAttribute("page", page);
        return "user/orders.html";
    }


    @GetMapping("orderInfo")
    public String toOrderInfo(HttpSession session){

        return "user/orderInfo.html";
    }

    @PostMapping("orderInfo")
    public String completeOrderInfo(HttpSession session, Order order){

        session.removeAttribute("totalPrice");
        order.setCreateDate(new Date());
        order.setStatus("unpaid");
        orderService.save(order);

        Cart cart = (Cart)session.getAttribute("cart");
        saveOrderItem(cart, order.getId());
        session.setAttribute("cart", null);
        return "index";
    }

    @RequestMapping("pay/{orderId}")
    public String pay(@PathVariable("orderId") Integer id){

        orderService.pay(id);
        return "redirect:/order/orders";
    }

    @RequestMapping("cancel/{orderId}")
    public String cancel(@PathVariable("orderId") Integer id){

        orderService.cancel(id);
        return "redirect:/order/orders";
    }

    @RequestMapping("refund/{orderId}")
    public String refund(@PathVariable("orderId") Integer id){

        orderService.refund(id);
        return "redirect:/order/orders";
    }

    @RequestMapping("recover/{orderId}")
    public String recover(@PathVariable("orderId") Integer id){

        orderService.recover(id);
        return "redirect:/order/orders";
    }

    private void saveOrderItem(Cart cart, Integer id){

        List<CartItem> cartItems = cart.getCartItems();
        cartItems.forEach(cartItem -> cartItemToOrderItem(cartItem, id));
    }

    private void cartItemToOrderItem(CartItem cartItem, Integer id){

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(id);
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItemService.save(orderItem);
    }
}
