package com.marian.flashcards.ai.service;

import com.marian.flashcards.ai.dto.GenerateDeckRequest;
import com.marian.flashcards.ai.dto.GeneratedCardDTO;
import com.marian.flashcards.model.Users;
import com.marian.flashcards.service.DeckService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeckGenerationService {

    private final AiGenerationService aiService;
    private final DeckService deckService;


    @Transactional
    public UUID generateDeck(GenerateDeckRequest request, Users user) {

        List<GeneratedCardDTO> generated =
                aiService.generate(request);

        return deckService.createFromGenerated(request, generated, user);
    }
}