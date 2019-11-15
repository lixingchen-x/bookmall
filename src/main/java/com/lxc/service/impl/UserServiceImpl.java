package com.lxc.service.impl;

import com.lxc.constants.AddResults;
import com.lxc.entity.User;
import com.lxc.helper.CartManager;
import com.lxc.helper.UserManager;
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

    @Autowired
    private CartManager cartManager;

    @Autowired
    private UserManager userManager;

    @Override
    public User getCompleteUser(User user) {

        User completeUser = findByUsername(user.getUsername());
        if (null != completeUser) {
            userManager.login(completeUser);
            cartManager.initCart();
            return completeUser;
        }
        return null;
    }

    @Override
    public void update(User user) {

        User oldUser = this.findById(user.getId());
        if (null != oldUser) {
            oldUser.setPassword(user.getPassword());
            oldUser.setEmail(user.getEmail());
            userRepository.saveAndFlush(oldUser);
        }
    }

    @Override
    public void deleteById(Integer id) {

        if (null != this.findById(id)) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public User findById(Integer id) {

        try {
            return userRepository.getOne(id);
        } catch (EntityNotFoundException e) {
            log.error("UserId = {} does not exist!", id);
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

        User user = this.findById(id);
        if (null != user) {
            user.changeRoleToAdmin();
            userRepository.saveAndFlush(user);
        }
    }

    @Override
    public void changeRoleToCustomer(Integer id) {

        User user = this.findById(id);
        if (null != user) {
            user.changeRoleToCustomer();
            userRepository.saveAndFlush(user);
        }
    }
}
