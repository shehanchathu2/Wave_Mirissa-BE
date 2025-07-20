package com.wave.Mirissa.dtos;

import java.math.BigDecimal;

public class CartItemRequest {
    private Long userId;
    private Long productId;
    private int quantity;
    private String size;
    private String customMaterial;
    private BigDecimal price;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCustomMaterial() {
        return customMaterial;
    }

    public void setCustomMaterial(String customMaterial) {
        this.customMaterial = customMaterial;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}