package com.wave.Mirissa.controllers;

import com.wave.Mirissa.dtos.AnswerDTO;
import com.wave.Mirissa.services.PersonalityAnalysisService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/virtual_try_on")
@CrossOrigin(origins = "*")
public class VirtualTryOnController {

    private final PersonalityAnalysisService personalityAnalysisService;

    public VirtualTryOnController(PersonalityAnalysisService personalityAnalysisService) {
        this.personalityAnalysisService = personalityAnalysisService;
    }

    @GetMapping("/api/questions")
    public Map<String, List<String>> getPersonalityQuestions() {
        List<String> questions = Arrays.asList(
                "How would your friends describe you in one word?",
                "Do you prefer adventurous experiences or calm and cozy environments?",
                "Which color do you feel most connected to?",
                "When making decisions, do you rely more on logic or emotions?",
                "If you were given a free weekend, how would you spend it?"
        );

        return Map.of("questions", questions);
    }

    @PostMapping("/api/answers")
    public Map<String, String> receiveAnswers(@RequestBody List<AnswerDTO> answers) {
        String personality = personalityAnalysisService.analyzePersonality(answers);
        System.out.println("📩 Received answers:");
        answers.forEach(a -> System.out.println(a.getQuestionText() + " -> " + a.getAnswer()));

        return Map.of("personality", personality);
    }
}
