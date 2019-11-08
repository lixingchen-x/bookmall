package com.lxc.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.io.Serializable;
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
    private List<OrderItem> orderItems;

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
}
