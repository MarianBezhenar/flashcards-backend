package com.marian.flashcards.ai.service;

import com.marian.flashcards.ai.dto.GenerateDeckRequest;
import com.marian.flashcards.ai.dto.GeneratedCardDTO;
import com.marian.flashcards.ai.dto.GeneratedCardsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiGenerationService {

    private final ChatClient chatClient;


    public List<GeneratedCardDTO> generate(GenerateDeckRequest request) {

        String prompt = buildPrompt(request);

        GeneratedCardsResponse response =
                chatClient.prompt()
                        .system("""
                            You are a flashcard generator.
                            Always return valid JSON.
                            Never return explanations.
                        """)
                        .user(prompt)
                        .call()
                        .entity(GeneratedCardsResponse.class);

        validate(response, request.quantity());

        return response.cards();
    }

    private String buildPrompt(GenerateDeckRequest request) {

        return """
                I am studying %s and focusing on %s.
                Generate %d flashcards at %s difficulty.

                %s

                Return JSON in this format:

                {
                  "cards": [
                    {
                      "question": "string",
                      "answer": "string"
                    }
                  ]
                }
                """.formatted(
                request.subject(),
                request.topic(),
                request.quantity(),
                request.difficulty(),
                request.sourceText() == null ? "" : request.sourceText()
        );
    }

    private void validate(GeneratedCardsResponse response, int expected) {

        if (response == null || response.cards() == null)
            throw new IllegalStateException("AI returned null");

        if (response.cards().size() != expected)
            throw new IllegalStateException("AI returned wrong number of cards");

        for (GeneratedCardDTO card : response.cards()) {
            if (card.question() == null || card.answer() == null)
                throw new IllegalStateException("Invalid card structure");
        }
    }
}