package com.lxc.controller;

import com.lxc.entity.User;
import com.lxc.exception.FailedSendingEmailException;
import com.lxc.service.UserService;
import com.lxc.utils.MailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;

@Controller
@Slf4j
public class RegisterController {

    private static final int MINIMUM_PASSWORD_SIZE = 6;
    private static final String REGISTER_SUCCESS = "注册成功！";

    @Autowired
    private UserService userService;

    @Autowired
    private MailUtils mailUtils;

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/doRegister")
    public String doRegister(User user, Model model) throws FailedSendingEmailException {

        User u = userService.findByUsername(user.getUsername());
        if (u != null) {
            model.addAttribute("msg", "用户名已存在");
            return "register";
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_SIZE) {
            model.addAttribute("msg", "密码长度请大于等于6");
            return "register";
        }
        userService.saveAsCustomer(user);
        try {
            mailUtils.sendMail(user.getEmail(), REGISTER_SUCCESS);
        } catch (MessagingException e) {
            log.error("用户{}的注册激活邮件发送失败", user.getUsername());
            throw new FailedSendingEmailException("Email does not work, please update a valid email.");
        }
        model.addAttribute("msg", "注册成功，请登录");
        return "login";
    }
}
