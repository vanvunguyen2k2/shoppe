package com.example.shoppe_project.modal.dto;

import com.example.shoppe_project.modal.entity.shippingUnit;
import com.example.shoppe_project.modal.entity.Status;
import com.example.shoppe_project.modal.entity.Type;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class ProducCreatetRequestDto {


    private int price;

    private Status status;

    private shippingUnit shippingUnit;

    @NotBlank(message = "ảnh không dược để trống")
    private String image;
    
    private Type type;

    @NotBlank(message = "Tên không được để trống")
    @Length(max = 5, message = "Tên không được quá 5 ký tự")
    private String name; // Điều kiện gì để khi thêm mới không bị lỗi
                        // không bị trùng
                            // không bị null
                            // không quá dài
                            // không chứa kí tự đặc biệt,.......
}
