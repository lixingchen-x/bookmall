package com.lxc.helper;

import com.lxc.entity.Cart;

public interface CartManager {

    /**
     * get the current cart from session
     *
     * @return
     */
    Cart getCurrentCart();

    /**
     * create a new cart in session with the current user
     *
     */
    void initCart();

    /**
     * update the current cart
     *
     * @param cart
     */
    void updateCart(Cart cart);
}
