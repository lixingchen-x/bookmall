package com.lxc.service.impl;

import com.lxc.constants.AddResults;
import com.lxc.entity.User;
import com.lxc.repository.UserRepository;
import com.lxc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
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
        } catch (EntityNotFoundException e) {
            log.error("UserId = {} does not exist!", user.getId());
        }
    }

    @Override
    public void deleteById(Integer id) {

        try {
            userRepository.getOne(id);
            userRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            log.error("UserId = {} does not exist!", id);
        }
    }

    @Override
    public User findById(Integer id) {

        try {
            return userRepository.getOne(id);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public void saveAsCustomer(User user) {

        user.changeRoleToCustomer();
        userRepository.saveAndFlush(user);
    }

    @Override
    public AddResults addUser(User user) {

        if (userRepository.findByUsername(user.getUsername()) == null) {
            saveAsCustomer(user);
            return AddResults.SUCCESS;
        }
        return AddResults.FAIL;
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
    public void changeRoleToAdmin(Integer id) {

        try {
            User user = userRepository.getOne(id);
            user.changeRoleToAdmin();
            userRepository.saveAndFlush(user);
        } catch (EntityNotFoundException e) {
            log.error("UserId = {} does not exist!", id);
        }
    }

    @Override
    public void changeRoleToCustomer(Integer id) {

        try {
            User user = userRepository.getOne(id);
            saveAsCustomer(user);
        } catch (EntityNotFoundException e) {
            log.error("UserId = {} does not exist!", id);
        }
    }
}
