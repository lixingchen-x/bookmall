package com.lxc.helper;

import com.lxc.entity.Cart;
import com.lxc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SessionAttributesHelper implements AttributesHelper{

    public final static String SESSION_USER = "user";
    public final static String SESSION_CART = "cart";

    @Autowired(required = false)
    private HttpServletRequest request;

    private void setAttribute(String name, Object value) {

        if (request != null) {

            request.getSession().setAttribute(name, value);
        }
    }

    private void removeAttribute(String name) {

        if (request != null) {

            request.getSession().removeAttribute(name);
        }
    }

    @Override
    public User getCurrentUser() {

        return (User) request.getSession().getAttribute(this.SESSION_USER);
    }

    @Override
    public Cart getCurrentCart() {

        return (Cart) request.getSession().getAttribute(this.SESSION_CART);
    }

    @Override
    public void login(User user) {

        setAttribute(this.SESSION_USER, user);
    }

    @Override
    public void logout() {

        removeAttribute(this.SESSION_USER);
    }

    @Override
    public void initCart() {

        setAttribute(this.SESSION_CART, new Cart(getCurrentUser()));
    }

    @Override
    public void updateCart(Cart cart) {

        setAttribute(this.SESSION_CART, cart);
    }
}
