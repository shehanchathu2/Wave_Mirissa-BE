package com.wave.Mirissa.services;

import com.wave.Mirissa.models.Customization;
import com.wave.Mirissa.models.Products;
import com.wave.Mirissa.repositories.CustomizationRepository;
import com.wave.Mirissa.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Products> getAllProducts() {
    return productRepository.findAll();
    }

    public Products getProductsByID(Long id) {

        return productRepository.findById(id).get();

    }

//    public Products addProduct(Products products) {
//         At this point, products.getImageUrl() already contains the Cloudinary URL from frontend
//        return productRepository.save(products);
//    }

    private CustomizationRepository customizationRepository;

    @Transactional
    public Products addProduct(Products products) {
        List<Customization> inputCustomizations = products.getCustomizations();
        if (inputCustomizations != null && !inputCustomizations.isEmpty()) {
            List<Long> ids = inputCustomizations.stream()
                    .map(Customization::getItem_id)
                    .toList();
            List<Customization> fullCustomizations = customizationRepository.findAllById(ids);
            products.setCustomizations(fullCustomizations);
        }
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
        existing.setQuantity(updatedProduct.getQuantity());
        existing.setCategory(updatedProduct.getCategory());
        existing.setDescription(updatedProduct.getDescription());
        existing.setGender(updatedProduct.getGender());
        existing.setCustomization(updatedProduct.getCustomization());
        existing.setAvailable(updatedProduct.isAvailable());

        existing.setImageUrl1(updatedProduct.getImageUrl1());
        existing.setImageUrl2(updatedProduct.getImageUrl2());
        existing.setImageUrl3(updatedProduct.getImageUrl3());

        return productRepository.save(existing);
    }
    

}
