package com.wave.Mirissa.dtos;

import java.math.BigDecimal;

public class MonthlyRevenueDTO {
    private int month;
    private BigDecimal total;
    private long orderCount; // 👈 new field

    public MonthlyRevenueDTO(int month, BigDecimal total, long orderCount) {
        this.month = month;
        this.total = total;
        this.orderCount = orderCount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(long orderCount) {
        this.orderCount = orderCount;
    }
}