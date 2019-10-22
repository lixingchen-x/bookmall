package com.lxc.controller;

import com.lxc.entity.User;
import com.lxc.repository.UserRepository;
import com.lxc.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户的表现层
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 用户后台管理页面
     * @param model
     * @return
     */
    @GetMapping("users")
    public String findAll(Model model){

        List<User> userList = userService.findAll();
        model.addAttribute("userList",userList);
        return "userManagement/userList.html";
    }

    /**
     * 用户添加的跳转
     * @return
     */
    @GetMapping("add")
    public String toAddUser(){
        return "userManagement/addUser.html";
    }

    /**
     * 用户添加
     * @param user
     * @return
     */
    @PostMapping("add")
    public String add(User user){

        userService.save(user);
        return "redirect:/user/users";
    }

    /**
     * 跳转到修改页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("update/{userId}")
    public String toUpdateUser(@PathVariable("userId") Integer id, Model model){

        User user = userService.findById(id);
        model.addAttribute("user",user);
        return "userManagement/editUser.html";
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @PutMapping("update")
    public String update(User user){

        userService.update(user);
        return "redirect:/user/users";
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping("delete/{userId}")
    public String delete(@PathVariable("userId") Integer id){

        userService.deleteById(id);
        return "redirect:/user/users";
    }

    @RequestMapping("settings")
    public String changeSettings(){

        // ToDo
        return "";
    }

}
