package com.lxc.repository;

import com.lxc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户的dao
 */
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUsername(String username);
}
