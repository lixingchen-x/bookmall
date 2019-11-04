package com.lxc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 购物车里物品的实体类
 * 对书的数量、用户id等属性进行封装
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem implements Serializable {

    private Book book;
    private Integer quantity;

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
