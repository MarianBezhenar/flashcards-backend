package com.marian.flashcards.model.dto;

import java.util.UUID;

public record CardResponseDTO(
        UUID id,
        String question,
        String answer
) {}