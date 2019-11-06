package com.lxc.entity;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.*;
import org.junit.Test;

public class UserTest {

    @Test
    public void getId_happyPath() {

        User user = new User();
        user.setId(1);
        assertThat(user.getId()).isEqualTo(1);
    }

    @Test
    public void getUsername_happyPath() {

        User user = new User();
        user.setUsername("abc");
        assertEquals("abc", user.getUsername());
    }

    @Test
    public void getPassword_happyPath() {

        User user = User.builder().password("abcdefg").build();
        assertEquals("abcdefg", user.getPassword());
    }

    @Test
    public void getEmail_happyPath() {

        User user = User.builder().email("abc@123.com").build();
        assertEquals("abc@123.com", user.getEmail());
    }

    @Test
    public void getRole_happyPath() {

        User user = new User();
        user.setRole(new Role(Role.ADMIN_ROLE_CODE));
        assertEquals("ADMIN", user.getRole().getName());
    }

    @Test
    public void builder_happyPath() {

        User user = User.builder().id(1).role(new Role(Role.ADMIN_ROLE_CODE)).build();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getRole().getName()).isEqualTo("ADMIN");
    }

    @Test
    public void toString_happyPath() {

        assertThat(User.builder().toString()).isEqualTo("User.UserBuilder(id=null, username=null, password=null, email=null, role=null)");
    }
}
