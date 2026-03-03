package com.marian.flashcards.ai.dto;

import java.util.List;

public record GeneratedCardsResponse(
        List<GeneratedCardDTO> cards
) {}