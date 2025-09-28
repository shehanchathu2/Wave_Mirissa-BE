package com.wave.Mirissa.services;

import com.wave.Mirissa.dtos.CategoryOverviewDTO;
import com.wave.Mirissa.models.*;
import com.wave.Mirissa.repositories.CustomizationRepository;
import com.wave.Mirissa.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }


    public List<Products> getAllProductsWithoutPersonality() {
        return productRepository.findByPersonalize("none");
    }

    public Products getProductsByID(Long id) {

        return productRepository.findById(id).get();

    }

//    public Products addProduct(Products products) {
//         At this point, products.getImageUrl() already contains the Cloudinary URL from frontend
//        return productRepository.save(products);
//    }

    @Autowired
    private CustomizationRepository customizationRepository;

    @Transactional
    public Products addProduct(Products products) {

        // Handle customizations
        List<Customization> inputCustomizations = products.getCustomizations();
        if (inputCustomizations != null && !inputCustomizations.isEmpty()) {
            List<Long> ids = inputCustomizations.stream()
                    .map(Customization::getItem_id)
                    .toList();
            List<Customization> fullCustomizations = customizationRepository.findAllById(ids);
            products.setCustomizations(fullCustomizations);

            String customizationNames = fullCustomizations.stream()
                    .map(Customization::getName)
                    .collect(Collectors.joining(","));
            products.setCustomization(customizationNames);
        } else {
            products.setCustomization(null);
        }

        // Set product type
        if (products instanceof Ring) {
            products.setTypeForDb("ring");
        } else if (products instanceof Necklace) {
            products.setTypeForDb("neckless");
        } else if (products instanceof WristBand) {
            products.setTypeForDb("wristband");
        } else if (products instanceof Bracelet) {
            products.setTypeForDb("bracelet");
        } else if (products instanceof Earring) {
            products.setTypeForDb("earring");
        } else if (products instanceof Anklet) {
            products.setTypeForDb("anklet");
        }

        // Default personalize value
        if (products.getPersonalize() == null || products.getPersonalize().isBlank()) {
            products.setPersonalize("none");
        }

        // 3D Model URL is directly mapped from the incoming JSON to products.modelUrl
        System.out.println("Saving product with 3D model URL: " + products.getModelUrl());

        return productRepository.save(products);
    }


    public Products getProduct(int id) {
        return productRepository.findById((long) id).orElse(null);
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }





    public Products updateProduct(Long id, Products updatedProduct) {
        Products existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(updatedProduct.getName());
        existing.setMaterial(updatedProduct.getMaterial());
        existing.setPrice(updatedProduct.getPrice());

        existing.setCategory(updatedProduct.getCategory());
        existing.setDescription(updatedProduct.getDescription());
        existing.setGender(updatedProduct.getGender());
        existing.setCustomization(updatedProduct.getCustomization());
        existing.setAvailable(updatedProduct.isAvailable());
        existing.setSkinToneTags(updatedProduct.getSkinToneTags());
        existing.setFaceShapeTags(updatedProduct.getFaceShapeTags());
        existing.setPersonalize(updatedProduct.getPersonalize());


        existing.setImageUrl1(updatedProduct.getImageUrl1());
        existing.setImageUrl2(updatedProduct.getImageUrl2());
        existing.setImageUrl3(updatedProduct.getImageUrl3());

//        existing.setProducttype(updatedProduct.getProducttype());
        if (updatedProduct instanceof Ring) {
            existing.setTypeForDb("ring");
        } else if (updatedProduct instanceof Necklace) {
            existing.setTypeForDb("neckless");
        } else if (updatedProduct instanceof WristBand) {
            existing.setTypeForDb("wristband");
        } else if (updatedProduct instanceof Bracelet) {
            existing.setTypeForDb("bracelet");
        } else if (updatedProduct instanceof Earring) {
            existing.setTypeForDb("earring");
        }else if (updatedProduct instanceof Anklet) {
            existing.setTypeForDb("anklet");
        }


        else {
            existing.setTypeForDb(null);
        }

        System.out.println("FaceShape: " + updatedProduct.getFaceShapeTags());
        System.out.println("SkinTone: " + updatedProduct.getSkinToneTags());

        return productRepository.save(existing);
    }



    public List<CategoryOverviewDTO> getProductTypeOverview() {
        List<Object[]> results = productRepository.countProductsByCategory();
        return results.stream() .map(r -> new CategoryOverviewDTO((String) r[0], (Long) r[1])) .toList();
    }

    public long getTotalProducts() {
        return productRepository.count();
    }

}