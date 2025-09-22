package com.wave.Mirissa.dtos;

import java.math.BigDecimal;

public class CartItemResponse {
    private Long id;           // CartItem id
    private Long productId;    // ✅ Add product id
    private String productName;
    private BigDecimal price;
    private int quantity;
    private String size;
    private String imageUrl;
    private String customMaterial;

    public CartItemResponse(Long id,
                            Long productId,
                            String productName,
                            BigDecimal price,
                            int quantity,
                            String imageUrl,
                            String customMaterial,
                            String size) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.customMaterial = customMaterial;
        this.size = size;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getCustomMaterial() { return customMaterial; }
    public void setCustomMaterial(String customMaterial) { this.customMaterial = customMaterial; }
}