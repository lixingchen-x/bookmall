package com.lxc.helper;

import com.lxc.entity.User;

public interface UserManager {

    User getCurrentUser();

    void login(User user);

    void logout();
}
