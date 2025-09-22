package com.wave.Mirissa.dtos;

import com.wave.Mirissa.models.Products;

import java.util.List;

public class AnalysisResponse {
    private String faceShape;
    private String skinTone;
    private List<Products> products;

    public AnalysisResponse(String faceShape, String skinTone, List<Products> products) {
        this.faceShape = faceShape;
        this.skinTone = skinTone;
        this.products = products;
    }

    public String getFaceShape() {
        return faceShape;
    }

    public String getSkinTone() {
        return skinTone;
    }

    public List<Products> getProducts() {
        return products;
    }
}