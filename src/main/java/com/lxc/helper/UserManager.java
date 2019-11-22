package com.lxc.helper;

import com.lxc.entity.User;

public interface UserManager {

    /**
     * get the current user from session
     *
     * @return
     */
    User getCurrentUser();

    /**
     * save the current user into session
     *
     * @param user
     */
    void login(User user);

    /**
     * remove the current user from session
     *
     */
    void logout();

    /**
     * update the current user
     * when user changes his password or email
     *
     * @param user
     */
    void updateUser(User user);
}
