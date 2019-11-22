package com.lxc.entity;

import lombok.*;

import java.io.Serializable;

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

    public OrderItem transferToOrderItem(Integer orderId) {

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setBookId(this.book.getId());
        orderItem.setQuantity(this.quantity);
        return orderItem;
    }

    public Integer getBookId() {

        return this.book.getId();
    }
}
