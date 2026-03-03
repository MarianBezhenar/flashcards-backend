package com.marian.flashcards.model.dto;

public record CreateDeckRequestDTO(
        String subject,
        String topic
) {}