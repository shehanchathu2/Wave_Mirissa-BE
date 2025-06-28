package com.wave.Mirissa.services;

import com.wave.Mirissa.models.Products;
import com.wave.Mirissa.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Products addProduct(Products products) {
        // At this point, products.getImageUrl() already contains the Cloudinary URL from frontend
        return productRepository.save(products);
    }

//    public Products addProduct(Products products, MultipartFile imageFile) throws IOException {
//        products.setImageName(imageFile.getOriginalFilename());
//        products.setImageType(imageFile.getContentType());
//        products.setImageData(imageFile.getBytes());
//        return productRepository.save(products);
//    }

    public Products getProduct(int id) {
        return productRepository.findById((long) id).orElse(null);
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

//    public Products updateProduct(Long id, Products updatedProduct) {
//        Products existingProduct = productRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        existingProduct.setName(updatedProduct.getName());
//        existingProduct.setMaterial(updatedProduct.getMaterial());
//        existingProduct.setPrice(updatedProduct.getPrice());
//        existingProduct.setQuantity(updatedProduct.getQuantity());
//        existingProduct.setCategory(updatedProduct.getCategory());
//        existingProduct.setAvailable(updatedProduct.isAvailable());
//        existingProduct.setDescription(updatedProduct.getDescription());
//        existingProduct.setCustomization(updatedProduct.getCustomization());
//        existingProduct.setGender(updatedProduct.getGender());
//        existingProduct.setImageUrl(updatedProduct.getImageUrl());
//
//        return productRepository.save(existingProduct);
//    }
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
=======


    // ProductService.java
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
>>>>>>> Stashed changes


    // ProductService.java
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

>>>>>>> Stashed changes

}
