package com.lxc.service.impl;

import com.lxc.entity.User;
import com.lxc.repository.UserRepository;
import com.lxc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void update(User user) {

        try {
            User oldUser = userRepository.getOne(user.getId());
            oldUser.setPassword(user.getPassword());
            oldUser.setEmail(user.getEmail());
            userRepository.saveAndFlush(oldUser);
        }catch (EntityNotFoundException e) {
            System.out.println("User does not exist!");
        }
    }

    @Override
    public void deleteById(Integer id) {

        try {
            userRepository.getOne(id);
            userRepository.deleteById(id);
        }catch (EntityNotFoundException e) {
            System.out.println("User does not exist!");
        }
    }

    @Override
    public User findById(Integer id) {

        try {
            return userRepository.getOne(id);
        }catch (EntityNotFoundException e) {
            System.out.println("User does not exist!");
            return null;
        }
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

        try {
            User user = userRepository.getOne(id);
            user.setAdmin();
            userRepository.saveAndFlush(user);
        }catch (EntityNotFoundException e) {
            System.out.println("User does not exist!");
        }
    }

    @Override
    public void setCustomer(Integer id) {

        try {
            User user = userRepository.getOne(id);
            user.setCustomer();
            userRepository.saveAndFlush(user);
        }catch (EntityNotFoundException e) {
            System.out.println("User does not exist!");
        }
    }
}
