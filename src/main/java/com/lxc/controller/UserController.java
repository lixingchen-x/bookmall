package com.lxc.controller;

import com.lxc.entity.User;
import com.lxc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/403")
    public String unauthorized() {

        return "403";
    }

    @GetMapping("updateProfile/{userId}")
    public String toUpdateProfile(@PathVariable("userId") Integer id, Model model) {

        model.addAttribute("user", userService.findById(id));
        return "user/profile.html";
    }

    @PutMapping("updateProfile")
    public String updateProfile(User user) {

        userService.update(user);
        return "index";
    }
}
