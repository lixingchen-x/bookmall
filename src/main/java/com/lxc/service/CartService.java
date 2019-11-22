package com.lxc.service;

import com.lxc.entity.Cart;
import com.lxc.entity.CartItem;

public interface CartService {

    /**
     * increase cartItem's quantity by 1
     *
     * @param bookId
     * @param cart
     */
    void increaseQuantity(Integer bookId, Cart cart);

    /**
     * decrease cartItem's quantity by 1
     *
     * @param bookId
     * @param cart
     */
    void decreaseQuantity(Integer bookId, Cart cart);

    /**
     * remove a cartItem from the current cart by bookId
     *
     * @param bookId
     * @param cart
     */
    void deleteBook(Integer bookId, Cart cart);

    /**
     * reset the cart
     *
     * @param cart
     */
    void reset(Cart cart);

    /**
     * create a cartItem by bookId and a certain quantity
     *
     * @param id
     * @param quantity
     * @return
     */
    CartItem createCartItem(Integer id, Integer quantity);
}
