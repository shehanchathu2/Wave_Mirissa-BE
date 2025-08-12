package com.wave.Mirissa.services;

import com.wave.Mirissa.models.MultipartInputStreamFileResource;
import com.wave.Mirissa.models.Products;
import com.wave.Mirissa.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class JewelryRecommendationService {

    private final WebClient webClient;
    private final ProductRepository productRepository;



    @Autowired
    public JewelryRecommendationService(WebClient.Builder webClientBuilder, ProductRepository productsRepository) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8000") // FastAPI backend URL
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .build();
        this.productRepository = productsRepository;
    }

    public List<Products> analyzeAndFetchRecommendations(MultipartFile image) throws IOException {
        Map<String, String> analysisResult = callFastApiForAnalysis(image);

        if (analysisResult == null || analysisResult.containsKey("error")) {
            String error = analysisResult != null ? analysisResult.get("error") : "Null response";
            throw new RuntimeException("Face analysis failed from AI Service: " + error);
        }

        String faceShape = analysisResult.get("face_shape");
        String skinTone = analysisResult.get("skin_tone");

        return productRepository.findByFaceShapeTagsIgnoreCaseAndSkinToneTagsIgnoreCase(faceShape, skinTone);
    }

    public Map<String, String> callFastApiForAnalysis(MultipartFile file) throws IOException {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename(), file.getSize()));

        Mono<Map<String, String>> responseMono = webClient.post()
                .uri("/analyze")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(formData))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});

        return responseMono.block();
    }


    private MultiValueMap<String, HttpEntity<?>> generateMultipartBody(MultipartFile file) throws IOException {
        MultiValueMap<String, HttpEntity<?>> body = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpEntity<byte[]> fileEntity = new HttpEntity<>(file.getBytes(), headers);
        body.add("file", fileEntity);
        return body;
    }
}
