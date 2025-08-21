package com.wave.Mirissa.repositories;

import com.wave.Mirissa.models.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByOrderId(String orderId);
    List<Order> findAllByStatus(String status); // e.g., "PAID"
    Page<Order> findAllByStatus(String status, Pageable pageable);


}
