package com.marian.flashcards.ai.controller;

import com.marian.flashcards.ai.dto.GenerateDeckRequest;
import com.marian.flashcards.ai.service.DeckGenerationService;
import com.marian.flashcards.model.Users;
import com.marian.flashcards.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/openai")
@RequiredArgsConstructor
public class DeckGenerationController {

    private final DeckGenerationService generationService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/generate-deck")
    public ResponseEntity<?> generate(@RequestBody GenerateDeckRequest request, Authentication authentication) {


        String email = authentication.getName();
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UUID deckId = generationService.generateDeck(request, user);

        return ResponseEntity.ok(
                Map.of(
                        "deckId", deckId,
                        "cardsCreated", request.quantity()
                )
        );
    }
}
