package com.xai.srvls.service;

import com.xai.srvls.model.Deck;
import com.xai.srvls.model.Flashcard;
import com.xai.srvls.model.User;
import com.xai.srvls.repository.DeckRepository;
import com.xai.srvls.repository.FlashcardRepository;
import com.xai.srvls.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Service for Flashcard-related operations
 */
@Service
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final DeckRepository deckRepository;
    private final UserRepository userRepository;

    @Autowired
    public FlashcardService(FlashcardRepository flashcardRepository, DeckRepository deckRepository, UserRepository userRepository) {
        this.flashcardRepository = flashcardRepository;
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
    }

    /**
     * Find a flashcard by ID
     * @param id The flashcard ID
     * @return An Optional containing the flashcard if found
     */
    @Cacheable(value = "flashcards", key = "#id", unless = "#result == null")
    public Optional<Flashcard> findById(UUID id) {
        return flashcardRepository.findById(id);
    }

    /**
     * Find all flashcards in a deck
     * @param deckId The deck ID
     * @param pageable Pagination information
     * @return A page of flashcards
     */
    public Page<Flashcard> findByDeckId(UUID deckId, Pageable pageable) {
        return deckRepository.findById(deckId)
                .map(deck -> flashcardRepository.findByDeck(deck, pageable))
                .orElse(Page.empty());
    }

    /**
     * Search for flashcards in a deck
     * @param deckId The deck ID
     * @param searchTerm The search term
     * @param pageable Pagination information
     * @return A page of matching flashcards
     */
    public Page<Flashcard> searchFlashcards(UUID deckId, String searchTerm, Pageable pageable) {
        return flashcardRepository.searchFlashcards(deckId, searchTerm, pageable);
    }

    /**
     * Create a new flashcard
     * @param flashcard The flashcard to create
     * @param deckId The deck ID
     * @param userId The creator's ID
     * @return The created flashcard
     */
    @Transactional
    public Flashcard create(Flashcard flashcard, UUID deckId, UUID userId) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new com.xai.srvls.exception.DeckNotFoundException(deckId.toString()));
        
        // Check if the user is the owner of the deck
        if (!deck.getOwner().getId().equals(userId)) {
            throw new com.xai.srvls.exception.UnauthorizedAccessException("this deck");
        }
        
        flashcard.setDeck(deck);
        return flashcardRepository.save(flashcard);
    }

    /**
     * Update a flashcard
     * @param id The flashcard ID
     * @param updatedFlashcard The updated flashcard data
     * @param userId The user ID (for authorization)
     * @return The updated flashcard
     */
    @Transactional
    @CacheEvict(value = "flashcards", key = "#id")
    public Flashcard update(UUID id, Flashcard updatedFlashcard, UUID userId) {
        return flashcardRepository.findById(id)
                .map(flashcard -> {
                    // Check if the user is the owner of the deck
                    if (!flashcard.getDeck().getOwner().getId().equals(userId)) {
                        throw new com.xai.srvls.exception.UnauthorizedAccessException("this flashcard");
                    }
                    
                    flashcard.setFrontContent(updatedFlashcard.getFrontContent());
                    flashcard.setBackContent(updatedFlashcard.getBackContent());
                    flashcard.setFrontImageUrl(updatedFlashcard.getFrontImageUrl());
                    flashcard.setBackImageUrl(updatedFlashcard.getBackImageUrl());
                    flashcard.setFrontAudioUrl(updatedFlashcard.getFrontAudioUrl());
                    flashcard.setBackAudioUrl(updatedFlashcard.getBackAudioUrl());
                    flashcard.setHint(updatedFlashcard.getHint());
                    flashcard.setTags(updatedFlashcard.getTags());
                    
                    return flashcardRepository.save(flashcard);
                })
                .orElseThrow(() -> new com.xai.srvls.exception.FlashcardNotFoundException(id.toString()));
    }

    /**
     * Delete a flashcard
     * @param id The flashcard ID
     * @param userId The user ID (for authorization)
     */
    @Transactional
    @CacheEvict(value = "flashcards", key = "#id")
    public void delete(UUID id, UUID userId) {
        flashcardRepository.findById(id)
                .ifPresent(flashcard -> {
                    // Check if the user is the owner of the deck
                    if (!flashcard.getDeck().getOwner().getId().equals(userId)) {
                        throw new com.xai.srvls.exception.UnauthorizedAccessException("this flashcard");
                    }
                    
                    flashcardRepository.delete(flashcard);
                });
    }

    /**
     * Find flashcards that are due for review
     * @param userId The user ID
     * @param deckId The deck ID (optional)
     * @param pageable Pagination information
     * @return A page of flashcards due for review
     */
    public Page<Flashcard> findFlashcardsDueForReview(UUID userId, UUID deckId, Pageable pageable) {
        return flashcardRepository.findFlashcardsDueForReview(userId, deckId, pageable);
    }
}
