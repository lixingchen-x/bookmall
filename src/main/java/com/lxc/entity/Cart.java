package com.lxc.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车的实体类
 */
public class Cart implements Serializable {

    private User user;
    private List<CartItem> cartItems;

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

        Double totalPrice = 0.0;
        for(CartItem cartItem:cartItems){
            totalPrice += cartItem.getSubTotal();
        }
        return totalPrice;
    }

    public void add(CartItem item){
        cartItems.add(item);
    }

    public void resetCart(){
        cartItems.removeAll(cartItems);
    }
}
