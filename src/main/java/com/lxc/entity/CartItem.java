package com.lxc.entity;

import java.io.Serializable;

/**
 * 购物车里物品的实体类
 * 对书的数量、用户id等属性进行封装
 */
public class CartItem implements Serializable {

    private Book book;
    private Integer quantity;

    public CartItem() {
    }

    public CartItem(Book book) {
        this.book = book;
    }

    public CartItem(Book book, Integer quantity) {

        this.book = book;
        this.quantity = quantity;
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

    void increaseQuantity() {

        this.quantity = this.quantity + 1;
    }

    void decreaseQuantity() {

        if (this.quantity > 0) {
            this.quantity = this.quantity - 1;
        }
    }

    public Double getSubTotal() {

        return this.book.getPrice() * this.quantity;
    }
}
