package com.wave.Mirissa.controllers;

import com.wave.Mirissa.models.Customization;
import com.wave.Mirissa.models.Products;
import com.wave.Mirissa.repositories.CustomizationRepository;
import com.wave.Mirissa.services.CustomizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:5173")
public class CustomizationController {
    @Autowired
    private CustomizationService customizationService;

    @GetMapping("/AllCustomizations")
    public ResponseEntity<List<Customization>> getAllCustomizations(){
        return new ResponseEntity<>(customizationService.getAllCustomizationList(), HttpStatus.OK);
    }

    @GetMapping("/Customizations/{itemId}")
    public Customization getCustomization(@PathVariable Long itemId){
        return customizationService.getCustomizationById(itemId);
    }

    @PostMapping("/AddCustomizations")
    public ResponseEntity<?> addCustomizations(@RequestBody Customization customization) {
        try {
            Customization savedCustomization = customizationService.addCustomization(customization);
            return new ResponseEntity<>(savedCustomization, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/Customizations/{itemId}")
    public ResponseEntity<?> updateCustomizations(
            @PathVariable Long itemId,
            @RequestPart("customizations") Customization customization) {

        try {
            System.out.println("imageUrl received: " + customization.getImageUrl());
            Customization updated = customizationService.updateCustomization(itemId, customization);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/Customizations/delete/{itemId}")
    public ResponseEntity<?> deleteCustomization (@PathVariable Long itemId ){
        try {
            customizationService.deleteCustomization(itemId);
            return ResponseEntity.ok("Customization deleted successfully.");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
}
}



    @GetMapping("/customizations")
    public ResponseEntity<List<Map<String, Object>>> getCustomizationNames() {
        List<Map<String, Object>> response = customizationService.getAllCustomizationList()
                .stream()
                .map(c -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", c.getItem_id());
                    map.put("name", c.getName());
                    return map;
                })
                .toList();
        return ResponseEntity.ok(response);
    }





}
