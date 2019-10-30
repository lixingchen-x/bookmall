package com.lxc.service;

import com.lxc.entity.User;
import java.util.List;

public interface UserService {

    User findByUsername(String username);

    void update(User user);

    void deleteById(Integer id);

    User findById(Integer id);

    void save(User user);

    List<User> findAll();

    void setRole(Integer id, String roleName);
}
