package com.example.shoppe_project.Service;

import com.example.shoppe_project.modal.dto.OrderRequestDto;
import com.example.shoppe_project.modal.entity.Account;
import com.example.shoppe_project.modal.entity.OrderStatus;
import com.example.shoppe_project.modal.entity.Order;

import java.util.List;

public interface IOrderService {

    Order create(OrderRequestDto dto);

    Order buyProduct(int orderId);

    Order cancelProduct(int orderId);

    List<Order> getAll();

    List<Order> findByStatus(OrderStatus oderStatus, int accountId);
}
