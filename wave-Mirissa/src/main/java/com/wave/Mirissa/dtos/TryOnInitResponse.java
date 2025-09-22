package com.wave.Mirissa.dtos;



public class TryOnInitResponse {
    private String personality;
    private String imageBase64; // data URL (e.g., data:image/png;base64,...)

    public TryOnInitResponse() {}

    public TryOnInitResponse(String personality, String imageBase64) {
        this.personality = personality;
        this.imageBase64 = imageBase64;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
