package com.lxc.entity;

import java.io.Serializable;

/**
 * 购物车里物品的实体类
 * 对书的数量、用户id等属性进行封装
 */
public class CartItem implements Serializable {

    private Book book;
    private Integer quantity;
    private Double subTotal;

    public CartItem(Book book, Integer quantity, Double subTotal) {

        this.book = book;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Double getSubTotal(){

        return subTotal;
    }

    public void setSubTotal(Double subTotal) {

        this.subTotal = subTotal;
    }

    public void increaseQuantity(){

        this.quantity = this.quantity + 1;
        this.subTotal = book.getPrice() * quantity;
    }

    public void decreaseQuantity(){

        this.quantity = this.quantity - 1;
        this.subTotal = book.getPrice() * quantity;
    }
}
