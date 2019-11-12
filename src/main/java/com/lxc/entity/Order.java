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

    @OneToMany(targetEntity = OrderItem.class,cascade = CascadeType.ALL)
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
    private String status;

    @Column
    private String receiver;

    public Order() {}

    public void changeStatusTo(OrderStatus status) {

        this.setStatus(status.getMsg());
    }

    public void addOrderItem(OrderItem orderItem) {

        this.orderItems.add(orderItem);
    }

    /*public OrderItem getByBookId(Integer id) {

        return orderItems.stream().filter(orderItem -> orderItem.getBookId().equals(id))
                .findFirst().orElse(null);
    }

    public boolean contains(Integer id) {

        return getByBookId(id) != null;
    }*/
}
