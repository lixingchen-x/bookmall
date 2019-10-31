package com.lxc.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车的实体类
 */
public class Cart implements Serializable {

    private User user;
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart() {}

    public Cart(User user) {
        this.user = user;
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

    public void resetCart() {

        cartItems.removeAll(cartItems);
    }

    public CartItem getByBookId(Integer id) {

        return cartItems.stream().filter(cartItem -> cartItem.getBook().getId().equals(id))
                .findFirst().orElse(null);
    }

    public void removeCartItem(Integer id) {

        CartItem cartItem = getByBookId(id);
        if(cartItem != null) {
            cartItems.remove(cartItem);
        }
    }

    public void decreaseQuantity(Integer id) {

        CartItem cartItem = getByBookId(id);
        if (cartItem != null) {
            cartItem.decreaseQuantity();
        }
    }

    public void increaseQuantity(Integer id) {

        CartItem cartItem = getByBookId(id);
        if (cartItem != null) {
            cartItem.increaseQuantity();
        }
    }

    public boolean contains(Integer id) {

        return getByBookId(id) != null;
    }

    public void addCartItem(CartItem cartItem) {

        CartItem item = this.getByBookId(cartItem.getBook().getId());
        if(item != null) {
            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
            return;
        }
        cartItems.add(cartItem);
    }
}
