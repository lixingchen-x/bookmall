package com.lxc.service.impl;

import com.lxc.entity.User;
import com.lxc.repository.UserRepository;
import com.lxc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void update(User user) {

        User oldUser = userRepository.getOne(user.getId());
        oldUser.setPassword(user.getPassword());
        oldUser.setEmail(user.getEmail());
        userRepository.saveAndFlush(oldUser);
    }

    @Override
    public void deleteById(Integer id) {

        userRepository.deleteById(id);
    }

    @Override
    public User findById(Integer id) {

        return userRepository.getOne(id);
    }

    @Override
    public void save(User user) {

        user.setCustomer();
        userRepository.saveAndFlush(user);
    }

    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {

        return userRepository.findAll();
    }

    @Override
    public void setAdmin(Integer id) {

        User user = userRepository.getOne(id);
        user.setAdmin();
        userRepository.saveAndFlush(user);
    }

    @Override
    public void setCustomer(Integer id) {

        User user = userRepository.getOne(id);
        user.setCustomer();
        userRepository.saveAndFlush(user);
    }
}
