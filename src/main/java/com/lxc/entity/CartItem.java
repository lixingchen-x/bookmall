package com.lxc.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 购物车里物品的实体类
 * 对书的数量、用户id等属性进行封装
 */
@Entity
@Table(name = "cart_item")
public class CartItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "quantity")
    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(){
        this.quantity = this.quantity + 1;
    }

    public void decreaseQuantity(){
        this.quantity = this.quantity - 1;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
