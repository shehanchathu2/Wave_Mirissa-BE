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

    public Products addProduct(Products products, MultipartFile imageFile) throws IOException {
        products.setImageName(imageFile.getOriginalFilename());
        products.setImageType(imageFile.getContentType());
        products.setImageData(imageFile.getBytes());
        return productRepository.save(products);


    }
}
