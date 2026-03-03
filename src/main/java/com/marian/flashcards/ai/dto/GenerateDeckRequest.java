package com.marian.flashcards.ai.dto;

public record GenerateDeckRequest(
        String subject,
        String topic,
        int quantity,
        String difficulty,
        String sourceText
) {}