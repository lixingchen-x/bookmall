package com.lxc.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车的实体类
 */
public class Cart implements Serializable {

    private User user;
    private List<CartItem> cartItems;
    private Double totalPrice;

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

        Double count = 0.0;
        for(CartItem item:cartItems){
            count = count + item.getSubTotal();
        }
        totalPrice = count;
        return totalPrice;
    }

    public void add(CartItem item){
        cartItems.add(item);
    }

    public void resetCart(){
        cartItems.removeAll(cartItems);
    }

    public void removeCartItem(CartItem item){
        cartItems.remove(item);
    }
    //ToDo
}
