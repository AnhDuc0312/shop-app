package com.project.shopapp.repositories;

import com.project.shopapp.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    //Tìm các đơn hàng của 1 user nào đó
    List<Order> findByUserId (Long userId);
}
