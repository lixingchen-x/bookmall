package com.lxc.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车的实体类
 */
@Getter
@Setter
public class Cart implements Serializable {

    private User user;
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart() {}

    public Cart(User user) {
        this.user = user;
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
        if (cartItem != null) {
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

    public Cart updateCart(CartItem cartItem) {

        CartItem item = this.getByBookId(cartItem.getBook().getId());
        if (item != null) {
            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
            return this;
        }
        cartItems.add(cartItem);
        return this;
    }
}
