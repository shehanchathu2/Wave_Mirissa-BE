package com.wave.Mirissa.controllers;

import com.wave.Mirissa.dtos.AnswerDTO;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/virtual_try_on")
@CrossOrigin(origins = "*")
public class VirtualTryOnController {

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
    public String receiveAnswers(@RequestBody List<AnswerDTO> answers) {
        // For now just print or log them
        answers.forEach(a -> {
            System.out.println("Q" + a.getQuestionId() + ": " + a.getQuestionText());
            System.out.println("Answer: " + a.getAnswer());
        });

        // Later we can process with NLP model
        return "Answers received successfully!";
    }



}
