package com.lxc.controller;

import com.lxc.entity.User;
import com.lxc.constants.AddResultEnum;
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

    @GetMapping("users")
    public String findAll(Model model) {

        model.addAttribute("users", userService.findAll());
        return "userManagement/users.html";
    }

    @GetMapping("add")
    public String toAddUser() {
        return "userManagement/addUser.html";
    }

    @PostMapping("add")
    public String add(User user, Model model) {

        if (AddResultEnum.FAIL.equals(userService.addUser(user))) {
            model.addAttribute("addUser", "新增用户失败，该用户名已存在！");
            return "userManagement/addUser.html";
        }
        return "redirect:/user/users";
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

    @RequestMapping("delete/{userId}")
    public String delete(@PathVariable("userId") Integer id) {

        userService.deleteById(id);
        return "redirect:/user/users";
    }

    @RequestMapping("changeRoleToAdmin/{userId}")
    public String changeRoleToAdmin(@PathVariable("userId") Integer id) {

        userService.changeRoleToAdmin(id);
        return "redirect:/user/users";
    }

    @RequestMapping("changeRoleToCustomer/{userId}")
    public String changeRoleToCustomer(@PathVariable("userId") Integer id) {

        userService.changeRoleToCustomer(id);
        return "redirect:/user/users";
    }
}
