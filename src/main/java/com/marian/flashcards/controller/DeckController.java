package com.marian.flashcards.controller;


import com.marian.flashcards.model.Deck;
import com.marian.flashcards.model.dto.DeckResponseDTO;
import com.marian.flashcards.service.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api")
public class DeckController {

    @Autowired
    DeckService service;

    //ALL
    @GetMapping("/decks")
    public List<DeckResponseDTO> getAllDecks(Authentication authentication){
        return service.getAllDecksResponses(authentication);
    }

    //GET ONE
    @GetMapping("/deck/{deckID}")
    public Deck getDeckById(@PathVariable UUID deckID){
        return service.getDeckById(deckID);
    }

    //DELETE DECK
    @DeleteMapping("/deck/{deckID}")
    public String deleteDeckById(@PathVariable UUID deckID){
        service.deleteDeckById(deckID);
        return "Deck deleted";
    }



}
