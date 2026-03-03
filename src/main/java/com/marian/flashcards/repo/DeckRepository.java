package com.marian.flashcards.repo;

import com.marian.flashcards.model.Deck;
import com.marian.flashcards.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeckRepository extends JpaRepository<Deck, UUID> {
    List<Deck> findByUsers(Users user);
}
