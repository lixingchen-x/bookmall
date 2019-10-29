package com.lxc.repository;

import com.lxc.entity.User;
import org.junit.Assert;
import org.junit.Test;

public class UserRepositoryTest extends BaseRepositoryTest {

    User user = new User("abc", "123456");

    @Test
    public void findByUsername_happyPath() {

        insertTestUser(user);
        User result = userRepository.findByUsername("abc");
        Assert.assertSame(user, result);
    }

    private void insertTestUser(User user) {

        userRepository.saveAndFlush(user);
    }
}
