package com.lxc.entity;

import lombok.*;

import java.io.Serializable;

/**
 * 购物车里物品的实体类
 * 对书的数量、用户id等属性进行封装
 */
@Getter
@Setter
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

    public OrderItem transferToOrderItem(Integer OrderId) {

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(OrderId);
        orderItem.setBookId(this.book.getId());
        orderItem.setQuantity(this.quantity);
        return orderItem;
    }
}
