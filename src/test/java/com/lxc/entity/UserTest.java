package com.lxc.entity;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class UserTest {

    User user = new User();

    @Test
    public void getUsername_happyPath() {

        user.setUsername("abc");
        assertEquals("abc", user.getUsername());
    }

    @Test
    public void getPassword_happyPath() {

        user.setPassword("abcdefg");
        assertEquals("abcdefg", user.getPassword());
    }

    @Test
    public void getEmail_happyPath() {

        user.setEmail("abc@123.com");
        assertEquals("abc@123.com", user.getEmail());
    }

    @Test
    public void getRole_happyPath() {

        user.setRole(new Role("ADMIN"));
        assertEquals("ADMIN", user.getRole().getName());
    }
}
