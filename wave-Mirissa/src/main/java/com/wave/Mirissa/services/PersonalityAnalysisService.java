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
                - Agreeableness
                - Conscientiousness
                - Extraversion
                - Neuroticism
                - Openness

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
            case "Agreeableness" ->
                    necklaceDetail = "This necklace reflects Agreeableness, the personality defined by kindness, cooperation, and compassion. The turtle pendant embodies patience, care, and a nurturing spirit, qualities that resonate with harmony and gentleness. The silver seahorse charm adds a sense of loyalty and quiet strength, symbolizing the supportive and cooperative nature of agreeable individuals. Complementing these, the wooden pearls bring warmth and simplicity, grounding the design in humility and natural beauty. Together, these elements form a piece that radiates empathy, friendliness, and the strength of peaceful connections.";
            case "Conscientiousness" ->
                    necklaceDetail = "This necklace embodies the essence of Conscientiousness, a personality marked by discipline, responsibility, and a strong sense of purpose. The sand dollar, with its symmetrical design, reflects order, balance, and reliability. The cowrie shell, historically valued as currency and protection, symbolizes careful planning, responsibility, and respect for tradition. The black beads add a touch of strength and stability, representing focus, self-control, and determination. Combined, these elements create a jewelry piece that mirrors the conscientious individual—structured, dependable, and guided by values of integrity and perseverance.";
            case "Extraversion" ->
                    necklaceDetail = "This necklace radiates the essence of Extraversion, a personality marked by energy, sociability, and a zest for life. The bright orange sea urchin shell stands as a bold centerpiece, symbolizing vitality, enthusiasm, and the ability to draw others in. At its heart, the shining white pearl reflects charisma and the inner spark that makes extroverts naturally captivating. Surrounding this, the gold starfish charms capture joy, warmth, and a love for connection, echoing the extrovert’s adaptability and eagerness to explore new experiences. Together, these vibrant elements create a striking piece that embodies confidence, liveliness, and the joy of engaging with the world.";
            case "Neuroticism" ->
                    necklaceDetail = "This necklace reflects the depth and sensitivity of Neuroticism, a personality often defined by emotional intensity and self-reflection. The silver whale tail with its engraving symbolizes the weight of inner emotions, while the embedded moonstone embodies shifting moods and heightened sensitivity, glowing differently with every light. The silver ring conveys cycles and continuity, mirroring the ebb and flow of emotional states. Finally, the black and white seed beads represent contrasts—calm and turbulence, clarity and doubt—capturing the duality within. Together, these elements form a piece that mirrors the inner world of those high in Neuroticism: complex, deeply emotional, and profoundly introspective.";
            case "Openness" ->
                    necklaceDetail = "The necklace is a reflection of Openness, a personality defined by creativity, curiosity, and a love for exploring new ideas. The Terebra Turritella shell, with its spiral structure, represents imagination and the endless journey of discovery. The blue sea glass, shaped and refined by the ocean, symbolizes uniqueness, curiosity, and the beauty of transformation. The starfish charm adds the essence of adaptability and exploration, reminding us of the ability to thrive in new environments. Finally, the dark purple bead conveys depth and individuality, highlighting the richness of creative thought. Together, these elements create a piece that celebrates adventurous spirits who embrace novelty, self-expression, and the wonders of the unknown.";
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
