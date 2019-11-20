package com.lxc.controller.admin;

import com.lxc.constants.ResultEnum;
import com.lxc.entity.User;
import com.lxc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class UserManageController {

    @Autowired
    private UserService userService;

    @GetMapping("user/users")
    public String findAll(Model model) {

        model.addAttribute("users", userService.findAll());
        return "userManagement/users.html";
    }

    @GetMapping("user/add")
    public String toAddUser() {
        return "userManagement/addUser.html";
    }

    @PostMapping("user/add")
    public String add(User user, Model model) {

        if (ResultEnum.FAIL.equals(userService.addUser(user))) {
            model.addAttribute("addUser", "新增用户失败，该用户名已存在！");
            return "userManagement/addUser.html";
        }
        return "redirect:/admin/user/users";
    }

    @RequestMapping("user/delete/{userId}")
    public String delete(@PathVariable("userId") Integer id) {

        userService.deleteById(id);
        return "redirect:/admin/user/users";
    }

    @RequestMapping("user/changeRoleToAdmin/{userId}")
    public String changeRoleToAdmin(@PathVariable("userId") Integer id) {

        userService.changeRoleToAdmin(id);
        return "redirect:/admin/user/users";
    }

    @RequestMapping("user/changeRoleToCustomer/{userId}")
    public String changeRoleToCustomer(@PathVariable("userId") Integer id) {

        userService.changeRoleToCustomer(id);
        return "redirect:/admin/user/users";
    }
}
