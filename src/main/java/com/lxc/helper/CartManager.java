package com.lxc.helper;

import com.lxc.entity.Cart;

public interface CartManager {

    Cart getCurrentCart();

    void initCart();

    void updateCart(Cart cart);
}
