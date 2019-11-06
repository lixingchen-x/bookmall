package com.lxc.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem extends Base implements Serializable {

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "book_id")
    private Integer bookId;

    @Column
    private Integer quantity;

    public OrderItem() {}
}
