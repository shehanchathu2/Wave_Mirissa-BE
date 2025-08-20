package com.wave.Mirissa.models;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products products;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToMany
    @JoinTable(
            name = "order_item_customizations",
            joinColumns = @JoinColumn(name = "order_item_id"),
            inverseJoinColumns = @JoinColumn(name = "customization_id")
    )

    private List<Customization> selectCustomization;

    private BigDecimal priceSnapshot;

    @Column(name = "product_name_snapshot")
    private String productNameSnapshot;

    @Column(name = "product_image_url_snapshot")
    private String productImageUrlSnapshot;

    public String getProductImageUrlSnapshot() {
        return productImageUrlSnapshot;
    }

    public void setProductImageUrlSnapshot(String productImageUrlSnapshot) {
        this.productImageUrlSnapshot = productImageUrlSnapshot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public List<Customization> getSelectCustomization() {
        return selectCustomization;
    }

    public void setSelectCustomization(List<Customization> selectCustomization) {
        this.selectCustomization = selectCustomization;
    }

    public BigDecimal getPriceSnapshot() {
        return priceSnapshot;
    }

    public void setPriceSnapshot(BigDecimal priceSnapshot) {
        this.priceSnapshot = priceSnapshot;
    }

    public String getProductNameSnapshot() {
        return productNameSnapshot;
    }

    public void setProductNameSnapshot(String productNameSnapshot) {
        this.productNameSnapshot = productNameSnapshot;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
