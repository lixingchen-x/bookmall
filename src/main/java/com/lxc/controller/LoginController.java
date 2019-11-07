package com.lxc.controller;

import com.lxc.entity.Cart;
import com.lxc.entity.User;
import com.lxc.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/index")
    public String index(HttpSession session) {

        session.setAttribute("key", null);
        session.setAttribute("keyword", null);
        return "index";
    }

    @RequestMapping("/doLogin")
    public String doLogin(HttpServletRequest request, User user) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
            User completeUser = userService.findByUsername(user.getUsername());
            request.setAttribute("user", completeUser);
            request.getSession().setAttribute("user", completeUser);
            request.getSession().setAttribute("cart", new Cart(completeUser)); //初始化一个空购物车
            return "index";
        } catch (UnknownAccountException e) {
            request.setAttribute("msg", "账号错误");
            return "login";
        } catch (IncorrectCredentialsException e) {
            request.setAttribute("msg", "密码错误");
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
