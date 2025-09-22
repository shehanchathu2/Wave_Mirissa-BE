package com.wave.Mirissa.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Products product;

    private int quantity;

    private BigDecimal price;

    @ManyToOne
    private Cart cart;

    private String size;
    private String customMaterial;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Products getProduct() { return product; }
    public void setProduct(Products product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getCustomMaterial() { return customMaterial; }
    public void setCustomMaterial(String customMaterial) { this.customMaterial = customMaterial; }
}