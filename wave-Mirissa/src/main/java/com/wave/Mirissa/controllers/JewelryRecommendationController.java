package com.wave.Mirissa.controllers;

import com.wave.Mirissa.dtos.AnalysisResponse;
import com.wave.Mirissa.dtos.AnalysisResponseCustomizations;
import com.wave.Mirissa.models.Products;
import com.wave.Mirissa.services.JewelryRecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class JewelryRecommendationController {

    private final JewelryRecommendationService jewelryRecommendationService;

    public JewelryRecommendationController(JewelryRecommendationService jewelryRecommendationService) {
        this.jewelryRecommendationService = jewelryRecommendationService;
    }

//    @PostMapping("/analyze")
//    public ResponseEntity<List<Products>> analyzeAndRecommend(@RequestParam("image")MultipartFile image){
//        try{
//            List<Products> recommendedProducts = jewelryRecommendationService.analyzeAndFetchRecommendations(image);
//            return ResponseEntity.ok(recommendedProducts);
//        }catch (IOException e){
//            return ResponseEntity.badRequest().build();
//        }catch (RuntimeException e){
//            return ResponseEntity.status(500).build();
//        }
//    }


    @PostMapping("/analyze")
    public ResponseEntity<AnalysisResponse> analyzeAndRecommend(@RequestParam("image") MultipartFile image) {
        try {
            AnalysisResponse response = jewelryRecommendationService.analyzeAndFetchRecommendations(image);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/analyze/customization")
    public ResponseEntity<AnalysisResponseCustomizations> analyzeAndRecommendCustomizations(@RequestParam("image") MultipartFile image) {
        try {
            AnalysisResponseCustomizations response = jewelryRecommendationService.analyzeAndFetchRecommendationsCustomizations(image);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).build();
        }
    }

}