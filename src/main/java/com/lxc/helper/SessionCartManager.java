package com.lxc.helper;

import com.lxc.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SessionCartManager implements CartManager {

    @Autowired
    private UserManager userManager;

    private final static String SESSION_CART = "cart";

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
    public Cart getCurrentCart() {

        return (Cart) request.getSession().getAttribute(SESSION_CART);
    }

    @Override
    public void initCart() {

        setAttribute(SESSION_CART, new Cart(userManager.getCurrentUser()));
    }

    @Override
    public void updateCart(Cart cart) {

        setAttribute(SESSION_CART, cart);
    }
}
