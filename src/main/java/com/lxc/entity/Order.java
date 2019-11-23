package com.lxc.entity;

import com.lxc.constants.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`order`")
@Getter
@Setter
public class Order extends BaseEntity implements Serializable {

    @OneToMany(targetEntity = OrderItem.class, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column
    private String address;

    @Column(name ="create_date")
    private Date createDate;

    @Column
    private Integer userId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Column
    private String receiver;

    public Order() {}

    public void addOrderItem(OrderItem orderItem) {

        this.orderItems.add(orderItem);
    }

    public void loadOrderItemsFromCart(Cart cart) {

        cart.getCartItems().forEach(cartItem -> this.addOrderItem(cartItem.transferToOrderItem(this.getId())));
    }
}
