package com.lxc.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色类
 */
@Entity
@Table(name = "role")
@Data
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
