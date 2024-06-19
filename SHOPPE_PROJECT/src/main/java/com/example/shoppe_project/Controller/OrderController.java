package com.example.shoppe_project.Controller;


import com.example.shoppe_project.Service.OrderService;
import com.example.shoppe_project.modal.dto.OrderRequestDto;
import com.example.shoppe_project.modal.entity.Account;
import com.example.shoppe_project.modal.entity.OrderStatus;
import com.example.shoppe_project.modal.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin("*")
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Order create(@RequestBody @Valid OrderRequestDto dto){
        return orderService.create(dto);
    }

    @PostMapping("/buy/{id}")
    public Order buyProduct(@PathVariable int id ){
        return orderService.buyProduct(id);
    }

    @PostMapping("/cancel/{id}")
    public Order cancelProduct(@PathVariable int id){
        return orderService.cancelProduct(id);
    }

    @GetMapping("/get-by-status")
    public List<Order> findByStatus(@RequestParam(required = false) OrderStatus status, @RequestParam int accountId){
        return orderService.findByStatus(status, accountId);

    }
}



