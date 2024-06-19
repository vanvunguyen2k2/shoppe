package com.example.shoppe_project.modal.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class loginRequest {
    private String username;
    private String password;
}
