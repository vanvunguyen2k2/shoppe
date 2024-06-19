package com.example.shoppe_project.modal.dto;

import com.example.shoppe_project.modal.entity.shippingUnit;
import com.example.shoppe_project.modal.entity.Status;
import com.example.shoppe_project.modal.entity.Type;
import lombok.Data;


@Data
public class ProductUpdateRequestDto {
    private int id;
    private int price;
    private Status status;
    private shippingUnit shippingUnit;
    private String image;
    private Type type;
    private String name;
}
