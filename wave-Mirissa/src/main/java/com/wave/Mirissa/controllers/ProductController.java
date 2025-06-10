package com.wave.Mirissa.controllers;

import com.wave.Mirissa.models.Products;
import com.wave.Mirissa.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/Allproducts")
    public ResponseEntity<List<Products>>  getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public Products getProduct(@PathVariable Long id){
        return productService.getProductsByID(id);
    }

    @PostMapping("/Addproducts")
    public ResponseEntity<?> addProduct(@RequestPart Products product, @RequestPart MultipartFile imageFile) {
        try {
            Products product1 = productService.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);


        }




    }


}
