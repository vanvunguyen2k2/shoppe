package com.example.shoppe_project.modal.dto;

import com.example.shoppe_project.modal.entity.Status;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class OrderRequestDto {
    private int productId;
    private int accountId;

    @Positive
    private int quantity;

}
