package com.lxc.service;

import com.lxc.constants.ResultEnum;
import com.lxc.entity.User;
import java.util.List;

public interface UserService {

    /**
     * retrieve a user's all attributes and return a complete user
     *
     * @param user
     * @return
     */
    User getCompleteUser(User user);

    /**
     * retrieve a user by a unique username
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * update a user with new information
     *
     * @param user
     */
    void update(User user);

    /**
     * delete a user by userId
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * retrieve a user by userId
     *
     * @param id
     * @return
     */
    User findById(Integer id);

    /**
     * set a user's role to customer
     * then save to database
     *
     * @param user
     */
    void saveAsCustomer(User user);

    /**
     * add a new user into database
     * return success or fail
     *
     * @param user
     * @return
     */
    ResultEnum addUser(User user);

    /**
     * retrieve all the users
     *
     * @return
     */
    List<User> findAll();

    /**
     * change a user's role to admin
     * then save to database
     *
     * @param id
     */
    void changeRoleToAdmin(Integer id);

    /**
     * change a user's role to customer
     * then save to database
     *
     * @param id
     */
    void changeRoleToCustomer(Integer id);
}
