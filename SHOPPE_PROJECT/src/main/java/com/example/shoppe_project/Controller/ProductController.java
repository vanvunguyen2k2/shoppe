package com.example.shoppe_project.Controller;


import com.example.shoppe_project.Service.AccountService;
import com.example.shoppe_project.Service.ProductService;
import com.example.shoppe_project.modal.dto.*;
import com.example.shoppe_project.modal.entity.Account;
import com.example.shoppe_project.modal.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin("*")
@Validated
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/search")
    public Page<Product> search(@RequestBody  SearchProductRequestDto request){
        return productService.search(request);
    }

    @GetMapping("/get-All")
    public List<Product> getAll(){
        return productService.getAll();
    }

    @PostMapping("/create")
    public Product create(@RequestBody @Valid ProducCreatetRequestDto dto){
        return productService.create(dto);
    }

    @GetMapping("/get_by_id/{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public Product get_by_id(@PathVariable(name = "id") int id){
        return productService.get_by_id(id);
    }

    @PutMapping("/update")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public Product update(@RequestBody ProductUpdateRequestDto dto){
        return productService.update(dto);
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public void delete(@PathVariable(name = "id") int id){
        productService.delete(id);
    }
}
