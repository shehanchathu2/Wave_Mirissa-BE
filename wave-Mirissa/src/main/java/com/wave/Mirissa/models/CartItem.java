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

    public Cart getCart() {
        return cart;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Products getProduct() {
        return product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
