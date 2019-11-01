package com.lxc.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`order`")
@Data
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(targetEntity = OrderItem.class,cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems;

    @Column
    private String address;

    @Column(name ="create_date")
    private Date createDate;

    @Column
    private String username;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    private String status;

    public Order() {}

    public Order(String username) {
        this.username = username;
    }
}
