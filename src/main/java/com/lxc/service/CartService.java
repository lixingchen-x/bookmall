package com.lxc.service;

import com.lxc.entity.Cart;

public interface CartService {

    void rollBackStockForCartReset(Cart cart);

    void saveOrderItem(Cart cart, Integer id);
}
