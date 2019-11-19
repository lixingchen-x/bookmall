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

        User user = new User();
        user.setPassword("abcdefg");

        assertEquals("abcdefg", user.getPassword());
    }

    @Test
    public void getEmail_happyPath() {

        User user = new User();
        user.setEmail("abc@123.com");

        assertEquals("abc@123.com", user.getEmail());
    }

    @Test
    public void getRole_happyPath() {

        User user = new User();
        Role admin = new Role(Role.ADMIN_ROLE_CODE);
        user.setRole(admin);

        assertThat(user.getRole()).isEqualTo(admin);
    }

    @Test
    public void builder_happyPath() {

        User user = User.builder().id(1).username("a").password("123456")
                .email("123@123").role(new Role(Role.ADMIN_ROLE_CODE)).build();

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getUsername()).isEqualTo("a");
        assertThat(user.getPassword()).isEqualTo("123456");
        assertThat(user.getEmail()).isEqualTo("123@123");
        assertThat(user.getRoleName()).isEqualTo("ADMIN");
    }

    @Test
    public void toString_happyPath() {

        assertThat(User.builder().toString()).isEqualTo("User.UserBuilder(id=null, username=null, password=null, email=null, role=null)");
    }

    @Test
    public void changeRoleToAdmin_happyPath() {

        User user = User.builder().build();

        user.changeRoleToAdmin();

        assertThat(user.getRoleName()).isEqualTo("ADMIN");
    }

    @Test
    public void changeRoleToCustomer_happyPath() {

        User user = User.builder().build();

        user.changeRoleToCustomer();

        assertThat(user.getRoleName()).isEqualTo("CUSTOMER");
    }
}
