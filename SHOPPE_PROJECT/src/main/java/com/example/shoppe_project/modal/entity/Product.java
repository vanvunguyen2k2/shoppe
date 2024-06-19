package com.example.shoppe_project.modal.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Product")
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @Column(name = "name", length = 250, unique = true, nullable = false)
    private String name;


    @Column(name = "image", nullable = false)
    private String image;


    @Column(name = "price", nullable = false)
    private int price;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;


    @Enumerated(EnumType.STRING)
    @Column(name = "shippingUnit")
    private shippingUnit shippingUnit;


    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;


}
