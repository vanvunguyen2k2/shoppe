package com.example.shoppe_project.Service;

import com.example.shoppe_project.modal.dto.*;
import com.example.shoppe_project.modal.entity.Account;
import com.example.shoppe_project.modal.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    List<Product> getAll();

    Product create(ProducCreatetRequestDto dto);

    Product get_by_id(int id);

    Page<Product> search(SearchProductRequestDto request);

    Product update(ProductUpdateRequestDto dto);

    void delete(int id);
}
