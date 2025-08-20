package com.wave.Mirissa.repositories;

import com.wave.Mirissa.models.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByOrderId(String orderId);
    // Paginated version
    @Query("""
    SELECT DISTINCT o
    FROM Order o
    LEFT JOIN FETCH o.user u
    LEFT JOIN FETCH o.orderItems oi
    LEFT JOIN FETCH oi.products p
    LEFT JOIN FETCH oi.selectCustomization c
    WHERE o.status = :status
""")
    List<Order> findAllByStatusWithDetails(@Param("status") String status, Pageable pageable);

    // Non-paginated version
    @Query("""
    SELECT DISTINCT o
    FROM Order o
    LEFT JOIN FETCH o.user u
    LEFT JOIN FETCH o.orderItems oi
    LEFT JOIN FETCH oi.products p
    LEFT JOIN FETCH oi.selectCustomization c
    WHERE o.status = :status
""")
    List<Order> findAllByStatusWithDetails(@Param("status") String status);




    List<Order> findByUserId(Long userId);
}
