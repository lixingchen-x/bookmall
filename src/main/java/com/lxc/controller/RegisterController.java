package com.lxc.controller;

import com.lxc.entity.User;
import com.lxc.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegisterController {

    private static final int MINIMUM_PASSWORD_SIZE = 6;

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/doRegister")
    public String doRegister(User user, Model model) {

        User u = userService.findByUsername(user.getUsername());
        if(u != null) {
            model.addAttribute("msg", "用户名已存在");
            return "register";
        }
        if(user.getPassword().length() < MINIMUM_PASSWORD_SIZE) {
            model.addAttribute("msg", "密码长度请大于6");
            return "register";
        }
        userService.save(user);
        model.addAttribute("msg", "注册成功，请登录");
        return "login";
    }
}
