package com.wave.Mirissa.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wave.Mirissa.models.Products;
import com.wave.Mirissa.services.PersonalityAnalysisService;
import com.wave.Mirissa.services.PythonTryOnClient;
import com.wave.Mirissa.services.NecklaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@RestController
@RequestMapping("/virtual_try_on")
public class TryOnController {

    @Autowired
    private PythonTryOnClient pythonTryOnClient;

    @Autowired
    private NecklaceService necklaceService;

    @Autowired
    private PersonalityAnalysisService personalityAnalysisService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // NEW: try on a user image with a necklace
    @PostMapping("/api/tryon")
    public ResponseEntity<?> tryOn(
            @RequestParam("userImage") MultipartFile userImage,
            @RequestParam("personality") String personality
    ) {
        if (userImage == null || userImage.isEmpty()) {
            return ResponseEntity.badRequest().body("{\"error\":\"User image is missing\"}");
        }

        try {
            // Get necklace URL from DB
            Products necklaceForUrl = necklaceService.getNecklaceByPersonality(personality);
            String necklaceUrl = (necklaceForUrl != null) ? necklaceForUrl.getImageUrl1() : null;
            if (necklaceUrl == null) {
                return ResponseEntity.status(404).body("{\"error\":\"No necklace found for this personality\"}");
            }

            // Save uploaded file temporarily
            File tempFile = File.createTempFile("upload-", userImage.getOriginalFilename());
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(userImage.getBytes());
            }

            String resultJson;
            try {
                // Call Python service
                resultJson = pythonTryOnClient.compose(tempFile, necklaceUrl);
            } finally {
                tempFile.delete();
            }

            // ✅ Parse string JSON into JsonNode before returning
            JsonNode jsonResponse = objectMapper.readTree(resultJson);

            // Fetch full necklace details
            Products necklace = necklaceService.getNecklaceByPersonality(personality);

            // Get necklace-personality description from OpenAI
            String personalityDescription = personalityAnalysisService
                    .generateNecklacePersonalityDescription(personality);

            // Wrap result + necklace details in one response
            ObjectNode responseNode = objectMapper.createObjectNode();
            responseNode.setAll((ObjectNode) jsonResponse);  // includes imageUrl + message from Python

            // add necklace details
            if (necklace != null) {
                responseNode.put("necklaceId", necklace.getProduct_id());
                responseNode.put("name", necklace.getName());
                responseNode.put("price", necklace.getPrice());
                responseNode.put("description", necklace.getDescription());
                responseNode.put("material", necklace.getMaterial());
                responseNode.put("imageUrl1", necklace.getImageUrl1());
                responseNode.put("imageUrl2", necklace.getImageUrl2());
                responseNode.put("imageUrl3", necklace.getImageUrl3());
            }

            // add AI-generated personality description
            responseNode.put("personalityDescription", personalityDescription);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseNode);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
