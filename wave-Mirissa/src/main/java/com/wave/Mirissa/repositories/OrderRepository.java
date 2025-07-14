package com.wave.Mirissa.repositories;

import com.wave.Mirissa.models.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByOrderId(String orderId);
}
