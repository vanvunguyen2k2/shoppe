package com.example.shoppe_project.modal.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "`Account`")
public class Account extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", length = 50, unique = true, nullable = false)
    private String username;

    @Column(name = "Role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "date")
    private Date date;

    @Column(name = "address", length = 250)
    private String address;

    @Column(name = "full_name", length = 50)
    private String full_name;

    @Column(name = "phone_number")
    private int phone_number;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "info", nullable = false, length = 300)
    private String info;
}
