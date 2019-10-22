package com.lxc.controller;

import com.lxc.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/doLogin")
    public String doLogin(HttpServletRequest request, User user, Model model){

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
            model.addAttribute("user", user);
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("cart", null); //初始化一个空购物车
            return "index";
        }catch (UnknownAccountException e){
            model.addAttribute("msg", "账号错误");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }

    @RequestMapping("doLogout")
    public String doLogout(){

        Subject subject = SecurityUtils.getSubject();
        try {
            subject.logout();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return "login";
        }
    }
}
