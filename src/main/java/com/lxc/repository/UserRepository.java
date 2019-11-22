package com.lxc.repository;

import com.lxc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    /**
     * retrieve user by username
     *
     * @param username
     * @return
     */
    User findByUsername(String username);
}
