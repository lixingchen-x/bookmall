package com.lxc.service;

import com.lxc.constants.AddResult;
import com.lxc.entity.User;
import java.util.List;

public interface UserService {

    User getCompleteUser(User user);

    User findByUsername(String username);

    void update(User user);

    void deleteById(Integer id);

    User findById(Integer id);

    void saveAsCustomer(User user);

    AddResult addUser(User user);

    List<User> findAll();

    void changeRoleToAdmin(Integer id);

    void changeRoleToCustomer(Integer id);
}
