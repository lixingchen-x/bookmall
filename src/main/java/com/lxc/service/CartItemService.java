package com.lxc.service;

import com.lxc.entity.CartItem;

public interface CartItemService {

    CartItem createCartItem(Integer id, Integer quantity);
}
