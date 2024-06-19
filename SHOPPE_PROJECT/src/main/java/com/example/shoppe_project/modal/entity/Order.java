package com.example.shoppe_project.modal.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "`Order`")
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_by")
    private Account account;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;


    @Enumerated(EnumType.STRING)
    @Column(name = "oderStatus")
    private OrderStatus orderStatus;
}
