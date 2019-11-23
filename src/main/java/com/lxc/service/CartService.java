package com.lxc.service;

import com.lxc.entity.Cart;
import com.lxc.entity.CartItem;

public interface CartService {

    /**
     * increase cartItem's quantity by 1
     */
    void increaseQuantity(Integer bookId, Cart cart);

    /**
     * decrease cartItem's quantity by 1
     */
    void decreaseQuantity(Integer bookId, Cart cart);

    void deleteBook(Integer bookId, Cart cart);

    void reset(Cart cart);

    CartItem createCartItem(Integer id, Integer quantity);
}
