package com.lxc.controller;

import com.lxc.entity.User;
import com.lxc.service.impl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

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
    public String doLogin(HttpServletRequest request, User user, Model model) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
            User completeUser = userService.findByUsername(user.getUsername());
            model.addAttribute("user", completeUser);
            request.getSession().setAttribute("user", completeUser);
            request.getSession().setAttribute("cart", null); //初始化一个空购物车
            return "index";
        }catch (UnknownAccountException e) {
            model.addAttribute("msg", "账号错误");
            return "login";
        }catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }

    @RequestMapping("doLogout")
    public String doLogout() {

        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            return "login";
        }
    }
}
