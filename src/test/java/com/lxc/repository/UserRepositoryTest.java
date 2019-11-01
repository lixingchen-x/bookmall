package com.lxc.repository;

import com.lxc.entity.User;
import org.junit.Assert;
import org.junit.Test;

public class UserRepositoryTest extends BaseRepositoryTes {

    @Test
    public void findByUsername_happyPath() {

        User user = insertTestUser("abc");
        User result = userRepository.findByUsername("abc");
        Assert.assertSame(user, result);
    }

    @Test
    public void findByUsername_shouldBeNull_ifUserDoesNotExist() {

        insertTestUser("abc");
        Assert.assertNull(userRepository.findByUsername("def"));
    }

    private User insertTestUser(String username) {

        User user = User.builder().username(username).build();
        userRepository.saveAndFlush(user);
        return user;
    }
}
