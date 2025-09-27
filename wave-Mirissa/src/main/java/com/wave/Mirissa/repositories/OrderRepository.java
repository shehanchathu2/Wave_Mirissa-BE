package com.wave.Mirissa.repositories;

import com.wave.Mirissa.dtos.RevenueStatsDTO;
import com.wave.Mirissa.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    // Pageable query without fetch joins
    Page<Order> findByStatus(String status, Pageable pageable);

    // For non-paginated fallback
    java.util.List<Order> findByStatus(String status);

    // Add this so controller can find order by orderId
    Optional<Order> findByOrderId(String orderId);

    // Return all orders with createdAt + amount + status
    @Query("SELECT new com.wave.Mirissa.dtos.RevenueStatsDTO(SUM(o.amount), COUNT(o), o.orderStatus) " +
            "FROM Order o GROUP BY o.orderStatus")
    List<RevenueStatsDTO> getRevenueStats();


    @Query("SELECT MONTH(o.createdAt) AS month, " +
            "SUM(o.amount) AS totalRevenue, " +
            "COUNT(o.id) AS totalOrders " +
            "FROM Order o " +
            "GROUP BY MONTH(o.createdAt) " +
            "ORDER BY MONTH(o.createdAt)")
    List<Object[]> getMonthlyRevenueAndOrders();

    Optional<Order> findById(Long id); // Already exists via JpaRepository

}
