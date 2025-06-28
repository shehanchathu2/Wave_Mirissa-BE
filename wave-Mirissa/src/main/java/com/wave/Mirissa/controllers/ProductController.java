package com.wave.Mirissa.controllers;

import com.wave.Mirissa.models.Products;
import com.wave.Mirissa.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
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

    @PostMapping("/addproducts")
    public ResponseEntity<?> addProduct(@RequestBody Products products) {
        try {
            Products savedProduct = productService.addProduct(products);
            return ResponseEntity.ok(savedProduct);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully.");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Products updatedProduct) {
        try {
            Products product = productService.updateProduct(id, updatedProduct);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



//    @PostMapping("/addproducts")
//    public ResponseEntity<?> addProduct( @RequestPart("products") Products products, @RequestPart("imageFile") MultipartFile imageFile) {
//        try {
//            Products product1 = productService.addProduct(products, imageFile);
//            return new ResponseEntity<>(product1, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @GetMapping("/product/{productId}/image")
//    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId) {
//        Products product = productService.getProduct(productId);
//        byte[] imageFile = product.getImageData();
//
//        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
//
//    }
}
