package com.lxc.controller;

import com.lxc.entity.User;
import com.lxc.helper.AttributesHelper;
import com.lxc.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private AttributesHelper attributesHelper;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/index")
    public String index() {

        return "index";
    }

    @RequestMapping("/doLogin")
    public String doLogin(Model model, User user) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
            User completeUser = userService.findByUsername(user.getUsername());
            model.addAttribute("user", completeUser);
            attributesHelper.login(completeUser);
            attributesHelper.initCart();
            return "index";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "账号错误");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }

    @RequestMapping("doLogout")
    public String doLogout() {

        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
}
