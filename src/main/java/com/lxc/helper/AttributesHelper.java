package com.lxc.helper;

import com.lxc.entity.Cart;
import com.lxc.entity.User;

public interface AttributesHelper {

    User getCurrentUser();

    Cart getCurrentCart();

    void login(User user);

    void logout();

    void initCart();

    void updateCart(Cart cart);
}
