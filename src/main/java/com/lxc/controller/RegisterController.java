package com.lxc.controller;

import com.lxc.result.Result;
import com.lxc.result.Results;
import com.lxc.entity.User;
import com.lxc.exception.FailedSendingEmailException;
import com.lxc.service.UserService;
import com.lxc.utils.DefaultMailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;

@Controller
@Slf4j
public class RegisterController {

    private static final int MINIMUM_PASSWORD_SIZE = 6;
    private static final String REGISTER_SUCCESS = "注册成功！";

    @Autowired
    private UserService userService;

    @Autowired
    private DefaultMailSender mailSender;

    @Autowired
    private Results results;

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/doRegister")
    @ResponseBody
    public Result doRegister(User user, Model model) throws FailedSendingEmailException {

        User u = userService.findByUsername(user.getUsername());
        if (u != null) {
            return results.usernameExists();
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_SIZE) {
            return results.passwordShort();
        }
        userService.saveAsCustomer(user);
        try {
            mailSender.send(user.getEmail(), REGISTER_SUCCESS);
        } catch (MessagingException e) {
            log.error("用户{}的注册激活邮件发送失败", user.getUsername());
            throw new FailedSendingEmailException("Email does not work, please update a valid email.");
        }
        return results.registerSuccess();
    }
}
