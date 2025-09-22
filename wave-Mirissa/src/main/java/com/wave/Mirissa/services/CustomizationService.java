package com.wave.Mirissa.services;

import com.wave.Mirissa.models.Customization;
import com.wave.Mirissa.repositories.CustomizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CustomizationService {
    @Autowired
    private CustomizationRepository customizationRepository;
    public List<Customization> getAllCustomizationList(){
        return customizationRepository.findAll();

    }
    public Customization getCustomizationById(Long itemId){
        return customizationRepository.findById(itemId).get();

    }

    public Customization addCustomization(Customization customization) {
        // Cloudinary image URL is already in customization.getImageUrl()
        return customizationRepository.save(customization);
    }



    public Customization updateCustomization(Long itemId, Customization updatedCustomization) {
        Customization existing = customizationRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Customization not found"));

        existing.setName(updatedCustomization.getName());
        existing.setPrice(updatedCustomization.getPrice());
        existing.setFaceShapeTags(updatedCustomization.getFaceShapeTags());
        existing.setSkinToneTags(updatedCustomization.getSkinToneTags());
        existing.setImageUrl(updatedCustomization.getImageUrl());

        return customizationRepository.save(existing);
    }


    public void deleteCustomization (Long itemId){
        if(!customizationRepository.existsById(itemId)){
            throw new IllegalArgumentException("DO not found with id:"+itemId);
        }else {
            customizationRepository.deleteById(itemId);
        }
    }
}