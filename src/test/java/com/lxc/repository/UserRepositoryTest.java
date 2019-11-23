package com.lxc.repository;

import com.lxc.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

public class UserRepositoryTest extends BaseEntityRepositoryTest {

    @Autowired
    protected UserRepository userRepository;

    @Test
    public void findByUsername_happyPath() {

        User user = insertTestUser("abc");

        User result = userRepository.findByUsername("abc");

        assertThat(result).isEqualTo(user);
    }

    @Test
    public void findByUsername_shouldReturnNull_ifUserDoesNotExist() {

        insertTestUser("abc");

        assertThat(userRepository.findByUsername("def")).isNull();
    }

    private User insertTestUser(String username) {

        User user = User.builder().username(username).build();
        userRepository.saveAndFlush(user);
        return user;
    }
}
