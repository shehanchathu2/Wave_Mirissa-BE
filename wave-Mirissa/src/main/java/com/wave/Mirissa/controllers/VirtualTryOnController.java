package com.wave.Mirissa.controllers;

import com.wave.Mirissa.dtos.AnswerDTO;
import com.wave.Mirissa.dtos.TryOnInitResponse;
import com.wave.Mirissa.services.PersonalityAnalysisService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.Base64;

@RestController
@RequestMapping("/virtual_try_on")
@CrossOrigin(origins = "*")
public class VirtualTryOnController {

    private final PersonalityAnalysisService personalityAnalysisService;

    public VirtualTryOnController(PersonalityAnalysisService personalityAnalysisService) {
        this.personalityAnalysisService = personalityAnalysisService;
    }

    // NEW: get questions for personality analysis
    @GetMapping("/api/questions")
    public Map<String, List<String>> getPersonalityQuestions() {
        List<String> questions = Arrays.asList(
                "When you have free time, how do you usually like to spend it?",
                "If you are planning something important, how do you usually approach it?",
                "How do you usually react when unexpected problems or stressful situations come up?",
                "When you are with friends or new people, how do you usually behave?",
                "How do you usually treat or think about other people’s feelings and opinions?"
        );
        return Map.of("questions", questions);
    }

        // NEW: receive answers from user, analyze them, and return personality
    @PostMapping("/api/answers")
    public Map<String, String> receiveAnswers(@RequestBody List<AnswerDTO> answers) {
        String personality = personalityAnalysisService.analyzePersonality(answers);
        System.out.println("📩 Received answers:");
        answers.forEach(a -> System.out.println(a.getQuestionText() + " -> " + a.getAnswer()));
        return Map.of("personality", personality);
    }

    // NEW: accept user image + personality, respond with dataURL (placeholder for now)
    @PostMapping(
            value = "/api/upload_user_image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TryOnInitResponse> uploadUserImage(
            @RequestParam("userImage") MultipartFile userImage,
            @RequestParam("personality") String personality
    ) {
        try {
            if (userImage == null || userImage.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // Convert the uploaded file to a data URL (for quick FE preview round-trip)
            String contentType = Optional.ofNullable(userImage.getContentType()).orElse("image/png");
            String base64 = Base64.getEncoder().encodeToString(userImage.getBytes());
            String dataUrl = "data:" + contentType + ";base64," + base64;
           // System.out.println(dataUrl);

            TryOnInitResponse resp = new TryOnInitResponse(personality, dataUrl);
            return ResponseEntity.ok(resp);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
