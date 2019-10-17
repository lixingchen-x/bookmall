package com.lxc.controller;

import com.lxc.entity.User;
import com.lxc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户的表现层
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String register(User user){

        User u=userRepository.findByUsername(user.getUsername());
        if(u != null){
            return "The user exists!";
        }
        userRepository.save(user);
        return "Register successfully!";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, User user){

        User u = userRepository.findByUsername(user.getUsername());
        if(u == null){
            return "The user does not exist!";
        }
        String incomingPassword = user.getPassword();
        String correctPassword = u.getPassword();
        if(incomingPassword.equals(correctPassword)){
            request.getSession().setAttribute("currentUser",user); //ToDo
            return "Login successfully!";
        }
        return "Login failed!";
    }
}
