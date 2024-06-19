package com.example.shoppe_project.Service;

import com.example.shoppe_project.Config.Exception.AppException;
import com.example.shoppe_project.Config.Exception.ErrorResponseEnum;
import com.example.shoppe_project.Repository.AccountRepository;
import com.example.shoppe_project.Repository.OrderRepository;
import com.example.shoppe_project.Repository.ProductRepository;
import com.example.shoppe_project.modal.dto.OrderRequestDto;
import com.example.shoppe_project.modal.entity.Account;
import com.example.shoppe_project.modal.entity.OrderStatus;
import com.example.shoppe_project.modal.entity.Order;
import com.example.shoppe_project.modal.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class OrderService implements IOrderService{

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;


    public Order create(OrderRequestDto dto) {

        Optional<Account> optionalAccount=accountRepository.findById(dto.getAccountId());
        if(optionalAccount.isEmpty()){
            throw new AppException(ErrorResponseEnum.NOT_FOUND_ACCOUNT);
        }

        Optional<Product> optionalProduct=productRepository.findById(dto.getProductId());
        if (optionalAccount.isEmpty()) {
            throw new AppException(ErrorResponseEnum.NOT_FOUND_PRODUCT);
        }
            Account account = optionalAccount.get();
            Product product = optionalProduct.get();

            Order order = new Order();
            order.setAccount(account);
            order.setProduct(product);
            order.setQuantity(dto.getQuantity());
            order.setOrderStatus(OrderStatus.PENDING);
            return orderRepository.save(order);
        }



    @Override
    public Order buyProduct(int orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            order.setOrderStatus(OrderStatus.DONE);
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public Order cancelProduct(int orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            order.setOrderStatus(OrderStatus.CANCEL);
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public List<Order> findByStatus(OrderStatus oderStatus, int accountId) {
        Optional<Account> optionalAccount=accountRepository.findById(accountId);
        if(optionalAccount.isEmpty()){
            throw new AppException(ErrorResponseEnum.NOT_FOUND_ACCOUNT);
        }
        if (oderStatus != null){
            return orderRepository.findAllByOrderStatusAndAccount_Id(oderStatus, accountId);

        }else {
            return orderRepository.findByAccount_Id(accountId);
        }
    }
}
