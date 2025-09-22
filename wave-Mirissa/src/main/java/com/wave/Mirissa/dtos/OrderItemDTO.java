package com.wave.Mirissa.dtos;

import java.util.List;

public class OrderItemDTO {
    private Long productId;
    private int quantity;
    private String size;
    private String customMaterial;
    private List<Long> customizationIds;
    private double finalPrice;

    // getters & setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getCustomMaterial() { return customMaterial; }
    public void setCustomMaterial(String customMaterial) { this.customMaterial = customMaterial; }

    public List<Long> getCustomizationIds() { return customizationIds; }
    public void setCustomizationIds(List<Long> customizationIds) { this.customizationIds = customizationIds; }

    public double getFinalPrice() { return finalPrice; }
    public void setFinalPrice(double finalPrice) { this.finalPrice = finalPrice; }
}