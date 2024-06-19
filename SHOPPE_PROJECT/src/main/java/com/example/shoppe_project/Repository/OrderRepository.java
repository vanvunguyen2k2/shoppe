package com.example.shoppe_project.Repository;

import com.example.shoppe_project.modal.entity.Account;
import com.example.shoppe_project.modal.entity.OrderStatus;
import com.example.shoppe_project.modal.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {


    List<Order> findAllByOrderStatusAndAccount_Id(OrderStatus oderStatus, int accountId);

    List<Order> findByAccount_Id(int accountId);

//    HQL
    @Query(value = "SELECT O FROM Order O WHERE O.orderStatus = :ODERSTATUS and O.account = :accountId")
    List<Order> findAllByOderStatusv2(@Param("ODERSTATUS") OrderStatus oderStatus, @Param("accountId") int accountId);

//    SQL
    @Query(value = "SELECT * FROM Order O WHERE O.oderStatus = :ODERSTATUS", nativeQuery = true)
    List<Order> findAllByOderStatusv3(@Param("ODERSTATUS") OrderStatus oderStatus);
}
