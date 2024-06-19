package com.example.shoppe_project.modal.dto;

import com.example.shoppe_project.modal.entity.Role;
import lombok.Data;

import java.util.Date;

@Data
public class AccountRequestDTO {
    private String username;
    private String password;
    private Date date;
    private String address;
    private String full_name;
    private int phone_number;
    private String email;
    private String info;
}
