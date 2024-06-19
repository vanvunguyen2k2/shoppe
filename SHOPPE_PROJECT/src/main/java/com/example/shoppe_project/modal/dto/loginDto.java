package com.example.shoppe_project.modal.dto;


import com.example.shoppe_project.modal.entity.Role;
import lombok.Data;

@Data
public class loginDto {
    private int userId;
    private String username;
    private Role role;
    private String fullName;
    private String userAgent; // THÔNG TIN TÊN TRÌNH DUYỆT ĐANG SỬ DỤNG
    private String token;
}
