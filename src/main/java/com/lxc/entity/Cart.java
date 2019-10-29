package com.lxc.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车的实体类
 */
public class Cart implements Serializable {

    private User user;
    private List<CartItem> cartItems;

    public Cart() {}

    public Cart(User user, List<CartItem> cartItems) {

        this.user = user;
        this.cartItems = cartItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Double getTotalPrice() {

        return cartItems.stream().mapToDouble(CartItem :: getSubTotal).sum();
    }

    public void add(CartItem item) {
        cartItems.add(item);
    }

    public void resetCart() {
        cartItems.removeAll(cartItems);
    }

    public CartItem getByBookId(Integer id) {

        return cartItems.stream().filter(cartItem -> cartItem.getBook().getId().equals(id))
                .findFirst().orElse(null);
    }

    public void removeCartItem(Integer id) {

        CartItem cartItem = getByBookId(id);
        cartItems.remove(cartItem);
    }

    public void decreaseQuantity(Integer id) {

        if (getByBookId(id) != null) {
            getByBookId(id).decreaseQuantity();
        }
    }

    public void increaseQuantity(Integer id) {

        if (getByBookId(id) != null) {
            getByBookId(id).increaseQuantity();
        }
    }

    public boolean contains(Integer id) {

        return getByBookId(id) != null;
    }
}
