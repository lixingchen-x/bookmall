package com.lxc.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户角色
 */
@Entity
@Table(name = "role")
@Getter
@Setter
public class Role implements Serializable {

    public static String ADMIN_ROLE_CODE = "ADMIN";
    public static String CUSTOMER_ROLE_CODE = "CUSTOMER";

    @Id
    @Column(name = "role_name")
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }
}
