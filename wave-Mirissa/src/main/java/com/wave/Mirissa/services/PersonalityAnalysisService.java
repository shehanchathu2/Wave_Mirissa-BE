package com.wave.Mirissa.services;

import com.wave.Mirissa.dtos.AnswerDTO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonalityAnalysisService {

    private final OpenAiChatModel chatModel;

    public PersonalityAnalysisService(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    // method for analyzing personality based on answers
    public String analyzePersonality(List<AnswerDTO> answers) {

        // Pre-validate answers
        boolean hasValidAnswer = answers.stream()
                .anyMatch(a -> a.getAnswer() != null && a.getAnswer().trim().length() > 2);

        if (!hasValidAnswer) {
            return "Cannot find personality, please try again.";
        }

        // Combine answers
        String combinedAnswers = answers.stream()
                .map(a -> "Q: " + a.getQuestionText() + "\nA: " + a.getAnswer())
                .collect(Collectors.joining("\n\n"));

        String prompt = """
                You are a personality analysis expert.
                Based on the user's answers, determine which ONE of the following personalities best matches them:
                - Elegant & Minimalist
                - Bold & Confident
                - Nature-Loving & Earthy
                - Trendy & Chic
                - Artistic & Creative

                If the answers are nonsensical, random, or meaningless (like "aadd, fhfh, 4rfe"), respond ONLY with:
                "Cannot find personality, please try again."

                The output MUST be ONLY one of the listed personality names above, 
                or the exact text: Cannot find personality, please try again.

                User answers:
                """ + combinedAnswers;

        try {
            // Use ChatClient prompt builder with a single user message (stateless)
            ChatClient freshClient = ChatClient.create(chatModel); // new instance
            return freshClient
                    .prompt()
                    .user(prompt)
                    .call()
                    .content()
                    .trim();

        } catch (Exception e) {
            e.printStackTrace();
            return "Cannot find personality, please try again.";
        }
    }

    // NEW METHOD: Generate necklace-personality description
    public String generateNecklacePersonalityDescription(String personality) {
        String necklaceDetail;

        switch (personality) {
            case "Elegant & Minimalist" ->
                    necklaceDetail = "A delicate silver chain with a small, refined pendant that emphasizes simplicity and timeless grace.";
            case "Bold & Confident" ->
                    necklaceDetail = "A statement gold necklace with strong geometric shapes that radiate power and fearlessness.";
            case "Nature-Loving & Earthy" ->
                    necklaceDetail = "A handcrafted necklace made of natural stones and wooden beads, symbolizing harmony with the earth.";
            case "Trendy & Chic" ->
                    necklaceDetail = "A layered necklace with modern charms, reflecting fashion-forward style and urban elegance.";
            case "Artistic & Creative" ->
                    necklaceDetail = "An abstract, colorful necklace with unique shapes and vibrant tones, representing imagination and originality.";
            default -> {
                return "Cannot generate description, unknown personality type.";
            }
        }

        String prompt = """
                You are a fashion personality expert. 
                Create a short paragraph (2-3 sentences) explaining how this necklace matches the personality type. 
                Mention both how the necklace symbolizes the traits of the personality and how it complements the wearer's identity.

                Personality: %s
                Necklace: %s
                """.formatted(personality, necklaceDetail);

        try {
            ChatClient freshClient = ChatClient.create(chatModel);
            return freshClient
                    .prompt()
                    .user(prompt)
                    .call()
                    .content()
                    .trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating necklace personality description.";
        }
    }
}
