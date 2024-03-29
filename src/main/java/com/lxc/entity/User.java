package com.lxc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity implements Serializable {

    @Column
    private String username;

    @Size(min = 6, message = "Password must be at least 6 characters!")
    @Column
    private String password;

    @Email
    @Column
    private String email;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "role_name")
    private Role role;

    @Builder
    public User(Integer id, String username, String password, String email, Role role) {

        super(id);
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getRoleName() {

        return this.role.getName();
    }

    public void changeRoleToAdmin() {

        this.role = new Role(Role.ADMIN_ROLE_CODE);
    }

    public void changeRoleToCustomer() {

        this.role = new Role(Role.CUSTOMER_ROLE_CODE);
    }
}
