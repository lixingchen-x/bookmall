package com.lxc.entity;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class RoleTest {

    @Test
    public void getName_happyPath() {

        Role role = new Role();
        role.setName(Role.ADMIN_ROLE_CODE);
        assertThat(role.getName()).isEqualTo("ADMIN");
    }
}
