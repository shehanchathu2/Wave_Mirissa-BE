package com.wave.Mirissa.dtos;

import java.math.BigDecimal;
import java.util.List;

public class CartResponse {
    private Long cartId;
    private BigDecimal total;
    private List<CartItemResponse> items;

    public CartResponse(Long cartId, BigDecimal total, List<CartItemResponse> items) {
        this.cartId = cartId;
        this.total = total;
        this.items = items;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<CartItemResponse> getItems() {
        return items;
    }

    public void setItems(List<CartItemResponse> items) {
        this.items = items;
    }
}