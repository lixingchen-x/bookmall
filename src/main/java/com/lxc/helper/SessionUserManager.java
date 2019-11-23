package com.lxc.helper;

import com.lxc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionUserManager implements UserManager {

    private static String SESSION_USER = "user";

    @Autowired
    private HttpSession session;

    private void setAttribute(String name, Object value) {

        if (session != null) {
            session.setAttribute(name, value);
        }
    }

    private void removeAttribute(String name) {

        if (session != null) {
            session.removeAttribute(name);
        }
    }

    @Override
    public User getCurrentUser() {

        return (User) session.getAttribute(SESSION_USER);
    }

    @Override
    public void login(User user) {

        setAttribute(SESSION_USER, user);
    }

    @Override
    public void logout() {

        removeAttribute(SESSION_USER);
    }

    @Override
    public void updateUser(User user) {

        setAttribute(SESSION_USER, user);
    }
}

