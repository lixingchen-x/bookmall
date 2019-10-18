package com.lxc.service.impl;

import com.lxc.entity.User;
import com.lxc.repository.UserRepository;
import com.lxc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void updateUser(User user) {

        User oldUser = userRepository.getOne(user.getId());
        oldUser.setPassword(user.getPassword());
        oldUser.setEmail(user.getEmail());
        userRepository.saveAndFlush(oldUser);
    }
    //ToDo
}
