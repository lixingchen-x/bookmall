package com.lxc.service;

import com.lxc.constants.ResultEnum;
import com.lxc.entity.User;
import java.util.List;

public interface UserService {

    /**
     * retrieve a user's all attributes and return a complete user
     */
    User getCompleteUser(User user);

    User findByUsername(String username);

    void update(User user);

    void deleteById(Integer id);

    User findById(Integer id);

    void saveAsCustomer(User user);

    /**
     * add a new user into database
     * return success or fail
     */
    ResultEnum addUser(User user);

    List<User> findAll();

    void changeRoleToAdmin(Integer id);

    void changeRoleToCustomer(Integer id);
}
