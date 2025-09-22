package com.wave.Mirissa.dtos;

import com.wave.Mirissa.models.Customization;


import java.util.List;

public class AnalysisResponseCustomizations {
    private String faceShape;
    private String skinTone;
    private List <Customization> customizations;

    public AnalysisResponseCustomizations(String faceShape, String skinTone, List<Customization> customizations) {
        this.faceShape = faceShape;
        this.skinTone = skinTone;
        this.customizations = customizations;
    }

    public String getFaceShape() {
        return faceShape;
    }

    public String getSkinTone() {
        return skinTone;
    }
    public List<Customization> getCustomizations(){
        return customizations;
    }
}