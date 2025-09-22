package com.wave.Mirissa.dtos;
public class CategoryOverviewDTO {
    private String productType;
    private Long count;
    public CategoryOverviewDTO(String productType, Long count) {
        this.productType = productType;
        this.count = count;
    }
    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public Long getCount() {
        return count;
    }
    public void setCount(Long count) {
        this.count = count;
    }
}