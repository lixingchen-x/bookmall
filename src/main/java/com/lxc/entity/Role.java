package com.lxc.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色类
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

    public static Role admin = new Role("ADMIN");

    public static Role customer = new Role("CUSTOMER");

    @Id
    @Column(name = "role_name")
    private String name;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
