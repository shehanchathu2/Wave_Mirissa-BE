package com.wave.Mirissa.dtos;

import com.wave.Mirissa.models.OrderStatus;
import java.math.BigDecimal;

public class RevenueStatsDTO {
    private BigDecimal totalAmount;
    private Long totalOrders;
    private OrderStatus orderStatus;

    public RevenueStatsDTO(BigDecimal totalAmount, Long totalOrders, OrderStatus orderStatus) {
        this.totalAmount = totalAmount;
        this.totalOrders = totalOrders;
        this.orderStatus = orderStatus;
    }

    // Getters
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}