package com.wave.Mirissa.dtos;

import com.wave.Mirissa.models.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;
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


    private String trackingNumber;
    private Date estimateDate;

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }
    private OrderStatus orderStatus;

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    // Getters and setters

    public static class UserDTO {
        private Long id;
        private String name;
        private String email;
        private AddressDTO address;
        // Getters and setters

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public AddressDTO getAddress() {
            return address;
        }

        public void setAddress(AddressDTO address) {
            this.address = address;
        }
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



    public static class AddressDTO {
        private String contactName;
        private String street;
        private String city;
        private String state;
        private String area;
        private String zipCode;
        private String phone;
        private String country;

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    private AddressDTO address;

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}