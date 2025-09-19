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


}
