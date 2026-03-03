package com.marian.flashcards.service;

import com.marian.flashcards.ai.dto.GenerateDeckRequest;
import com.marian.flashcards.ai.dto.GeneratedCardDTO;
import com.marian.flashcards.model.Card;
import com.marian.flashcards.model.Deck;
import com.marian.flashcards.model.Users;
import com.marian.flashcards.model.dto.CardResponseDTO;
import com.marian.flashcards.model.dto.DeckResponseDTO;
import com.marian.flashcards.repo.DeckRepository;
import com.marian.flashcards.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeckService {

    private final DeckRepository deckRepository;
    @Autowired
    private UserRepository userRepository;

    public UUID createFromGenerated(
            GenerateDeckRequest request,
            List<GeneratedCardDTO> generatedCards,
            Users user
    ){

        Deck deck = Deck.builder()
                .subject(request.subject())
                .topic(request.topic())
                .createdAt(LocalDateTime.now())
                .cards(new ArrayList<>()) // <--- initialize the cards list
                .users(user)
                .build();

        for (GeneratedCardDTO dto : generatedCards) {

            Card card = Card.builder()
                    .question(dto.question())
                    .answer(dto.answer())
                    .deck(deck)
                    .createdAt(LocalDateTime.now())
                    .build();

            deck.getCards().add(card);
        }

        deckRepository.save(deck);

        return deck.getId();
    }

    public List<DeckResponseDTO> getAllDecksResponses(Authentication authentication) {

        String email = authentication.getName();
        Users user = userRepository.findByEmail(email)
                .orElseThrow();



        List<Deck> decks = deckRepository.findByUsers(user);

        List<DeckResponseDTO> deckResponses = new ArrayList<>();

        for(Deck deck: decks){

            List<CardResponseDTO> cardResponses = new ArrayList<>();
            for(Card card: deck.getCards()){
                CardResponseDTO cardResponse = new CardResponseDTO(
                        card.getId(),
                        card.getQuestion(),
                        card.getAnswer()
                );
                cardResponses.add(cardResponse);
            }

            DeckResponseDTO deckResponse = new DeckResponseDTO(
                        deck.getId(),
                        deck.getSubject(),
                        deck.getTopic(),
                        cardResponses
                        );

            deckResponses.add(deckResponse);

        }

        return deckResponses;
    }

    public Deck getDeckById(UUID deckID) {
        Optional<Deck> deck = deckRepository.findById(deckID);

        return deck.orElse(null);
    }

    public void deleteDeckById(UUID deckID) {
        deckRepository.deleteById(deckID);
    }
}


























