package com.lxc.service;

import com.lxc.entity.Cart;
import com.lxc.entity.CartItem;

public interface CartService {

    void increaseQuantity(Integer bookId, Cart cart);

    void decreaseQuantity(Integer bookId, Cart cart);

    void deleteBook(Integer bookId, Cart cart);

    void reset(Cart cart);

    CartItem createCartItem(Integer id, Integer quantity);
}
