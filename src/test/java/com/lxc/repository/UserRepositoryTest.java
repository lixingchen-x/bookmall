package com.lxc.repository;

import com.lxc.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryTest extends BaseEntityRepositoryTest {

    @Autowired
    protected UserRepository userRepository;

    @Test
    public void findByUsername_happyPath() {

        User user = insertTestUser("abc");

        User result = userRepository.findByUsername("abc");

        Assert.assertSame(user, result);
    }

    @Test
    public void findByUsername_shouldReturnNull_ifUserDoesNotExist() {

        insertTestUser("abc");

        Assert.assertNull(userRepository.findByUsername("def"));
    }

    private User insertTestUser(String username) {

        User user = User.builder().username(username).build();
        userRepository.saveAndFlush(user);
        return user;
    }
}
