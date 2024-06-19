package com.example.shoppe_project.modal.entity;

import org.springframework.security.core.GrantedAuthority;


/**
 * implements GrantedAuthority: ĐANG COI MỘT ĐỐI TƯỢNG ENUM ROLE LÀ 1 QUYỀN TRONG SPRING SECURITY
 */
public enum Role implements GrantedAuthority {

    CUSTOMER, SELlER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
