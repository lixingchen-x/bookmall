package com.lxc.repository;

import com.lxc.entity.User;
import org.junit.Assert;
import org.junit.Test;

public class UserRepositoryTest extends Base {

    @Test
    public void findByUsername_happyPath() {

        User user = insertTestUser("abc", null);
        User result = userRepository.findByUsername("abc");
        Assert.assertSame(user, result);
    }

    @Test
    public void findByUsername_shouldBeNull_ifUserDoesNotExist() {

        insertTestUser("abc", null);
        Assert.assertNull(userRepository.findByUsername("def"));
    }

    private User insertTestUser(String username, String password) {

        User user = new User(username, password);
        userRepository.saveAndFlush(user);
        return user;
    }
}
