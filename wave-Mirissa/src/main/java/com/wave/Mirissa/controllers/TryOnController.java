package com.wave.Mirissa.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            String necklaceUrl = necklaceService.getNecklaceImageUrlByPersonality(personality);
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

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonResponse);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
