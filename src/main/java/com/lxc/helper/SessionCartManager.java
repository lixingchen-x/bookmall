package com.lxc.helper;

import com.lxc.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionCartManager implements CartManager {

    private static String SESSION_CART = "cart";

    @Autowired
    private UserManager userManager;

    @Autowired
    private HttpSession session;

    private void setAttribute(String name, Object value) {

        if (session != null) {
            session.setAttribute(name, value);
        }
    }

    @Override
    public Cart getCurrentCart() {

        return (Cart) session.getAttribute(SESSION_CART);
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
