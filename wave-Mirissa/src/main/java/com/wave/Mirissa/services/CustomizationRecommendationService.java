package com.wave.Mirissa.services;

import com.wave.Mirissa.models.Customization;
import com.wave.Mirissa.models.MultipartInputStreamFileResource;
import com.wave.Mirissa.repositories.CustomizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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
public class CustomizationRecommendationService {
    private final WebClient webClient;
    private final CustomizationRepository customizationRepository;

    @Autowired
    public CustomizationRecommendationService(WebClient.Builder webClientBuilder, CustomizationRepository customizationRepository) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8000")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .build();
        this.customizationRepository = customizationRepository;
    }

    public List<Customization> analyzeAndFetchRecommendedCustomizations(MultipartFile image) throws IOException {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("file", new MultipartInputStreamFileResource(image.getInputStream(), image.getOriginalFilename(), image.getSize()));

        Mono<Map<String, String>> responseMono = webClient.post()
                .uri("/Cus_analyze")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(formData))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});

        Map<String, String> result = responseMono.block();

        if (result == null || result.containsKey("error")) {
            throw new RuntimeException("FastAPI analysis failed: " + (result != null ? result.get("error") : "No response"));
        }

        String faceShape = result.get("face_shape");
        String skinTone = result.get("skin_tone");

        return customizationRepository.findByFaceShapeTagsIgnoreCaseAndSkinToneTagsIgnoreCase(faceShape, skinTone);
    }
}
