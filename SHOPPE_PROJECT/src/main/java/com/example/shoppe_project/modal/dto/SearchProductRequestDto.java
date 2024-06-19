package com.example.shoppe_project.modal.dto;

import com.example.shoppe_project.modal.entity.shippingUnit;
import com.example.shoppe_project.modal.entity.Status;
import com.example.shoppe_project.modal.entity.Type;
import lombok.Data;

import java.util.Set;

@Data
public class SearchProductRequestDto extends BaseRequest{
    private String name;
    private int minPrice;
    private int maxPrice;
    private Set<Status> status;
    private Set<shippingUnit> shippingUnit;
    private Set<Type> type;
}
