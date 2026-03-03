package com.marian.flashcards.model.dto;

import java.util.List;
import java.util.UUID;

public record DeckResponseDTO(
        UUID id,
        String subject,
        String topic,
        List<CardResponseDTO> totalCards
) {}