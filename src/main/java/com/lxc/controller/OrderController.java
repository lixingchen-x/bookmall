package com.lxc.controller;

import com.lxc.constants.OrderStatusEnum;
import com.lxc.entity.*;
import com.lxc.exception.FailedSendingEmailException;
import com.lxc.helper.CartManager;
import com.lxc.helper.CurrentCart;
import com.lxc.helper.CurrentUser;
import com.lxc.service.OrderService;
import com.lxc.utils.DefaultMailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@Slf4j
@Controller
@RequestMapping("/order")
public class OrderController {

    private static final String PAY_SUCCESS = "付款成功，请耐心等待发货哦~";

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartManager cartManager;

    @Autowired
    private DefaultMailSender mailSender;

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
    public String completeOrderInfoThenSave(@CurrentUser User user, @CurrentCart Cart cart, Order order) {

        orderService.loadOrderItemAndComplete(user, cart, order);
        orderService.save(order);
        cartManager.initCart();
        return "index";
    }

    @RequestMapping("pay")
    public String pay(@CurrentUser User user, @RequestParam(value = "orderId") Integer id,
                      @RequestParam(defaultValue = "0") Integer page, Model model) throws FailedSendingEmailException {

        model.addAttribute("page", page);
        orderService.pay(id);
        sendEmail(user);
        return "forward:/order/orders";
    }

    @RequestMapping("cancel")
    public String cancel(@RequestParam(value = "orderId") Integer id, @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("page", page);
        orderService.cancel(id);
        return "forward:/order/orders";
    }

    @RequestMapping("refund")
    public String refund(@RequestParam(value = "orderId") Integer id, @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("page", page);
        orderService.setStatus(OrderStatusEnum.UNPAID, id);
        return "forward:/order/orders";
    }

    @RequestMapping("recover")
    public String recover(@RequestParam(value = "orderId") Integer id, @RequestParam(defaultValue = "0") Integer page, Model model) {

        model.addAttribute("page", page);
        orderService.setStatus(OrderStatusEnum.UNPAID, id);
        return "forward:/order/orders";
    }

    private void sendEmail(User user) throws FailedSendingEmailException {

        try {
            mailSender.send(user.getEmail(), PAY_SUCCESS);
        } catch (MessagingException e) {
            log.error("用户{}的付款成功邮件发送失败", user.getUsername());
            throw new FailedSendingEmailException("付款成功邮件发送失败，等待重发请求");
        }
    }
}
