package com.lxc.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车的实体类
 */
public class Cart implements Serializable {

    private Integer userId;
    private List<CartItem> cartItems;

    public Cart(Integer userId, List<CartItem> cartItems) {

        this.userId = userId;
        this.cartItems = cartItems;
    }

    public Cart(Integer userId) {

        this.userId = userId;
        this.cartItems = new ArrayList<>();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
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
