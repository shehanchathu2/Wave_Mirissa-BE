package com.wave.Mirissa.dtos;

import java.math.BigDecimal;
import java.util.List;

public class OrderDetailedDTO {

    private Long id;
    private String orderId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String paymentMethod;
    private String payhereRef;
    private String createdAt;
    private UserDTO user;
    private List<ProductWithCustomizationDTO> products;

    // Getters and setters

    public static class UserDTO {
        private Long id;
        private String name;
        private String email;

        // Getters and setters

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    public static class ProductWithCustomizationDTO {
        private Long productId;
        private String name;
        private BigDecimal price;
        private List<String> customizations;

        // Getters and setters

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
        public List<String> getCustomizations() { return customizations; }
        public void setCustomizations(List<String> customizations) { this.customizations = customizations; }
    }

    // Main DTO getters and setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getPayhereRef() { return payhereRef; }
    public void setPayhereRef(String payhereRef) { this.payhereRef = payhereRef; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }
    public List<ProductWithCustomizationDTO> getProducts() { return products; }
    public void setProducts(List<ProductWithCustomizationDTO> products) { this.products = products; }
}
