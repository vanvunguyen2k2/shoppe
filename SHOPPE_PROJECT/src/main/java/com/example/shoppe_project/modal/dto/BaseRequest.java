package com.example.shoppe_project.modal.dto;

import lombok.Data;

@Data
public class BaseRequest {
    protected int page;
    protected int size;
    protected String sortField;
    protected String sortType;

}
