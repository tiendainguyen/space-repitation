package com.xai.srvls.service;

import com.xai.srvls.model.Deck;
import com.xai.srvls.model.User;
import com.xai.srvls.repository.DeckRepository;
import com.xai.srvls.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for Deck-related operations
 */
@Service
public class DeckService {
    
    private final DeckRepository deckRepository;
    private final UserRepository userRepository;
    
    @Autowired
    public DeckService(DeckRepository deckRepository, UserRepository userRepository) {
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
    }
    
    /**
     * Find a deck by ID
     * @param id the deck ID
     * @return an Optional containing the deck if found
     */
    @Cacheable(value = "decks", key = "#id", unless = "#result == null")
    public Optional<Deck> findById(UUID id) {
        return deckRepository.findById(id);
    }
    
    /**
     * Find all decks owned by a user
     * @param userId the user ID
     * @return a list of decks
     */
    public List<Deck> findByOwnerId(UUID userId) {
        return userRepository.findById(userId)
                .map(deckRepository::findByOwner)
                .orElse(List.of());
    }
    
    /**
     * Find all decks owned by a user with pagination
     * @param userId the user ID
     * @param pageable pagination info
     * @return a page of decks
     */
    public Page<Deck> findByOwnerId(UUID userId, Pageable pageable) {
        return userRepository.findById(userId)
                .map(user -> deckRepository.findByOwner(user, pageable))
                .orElse(Page.empty());
    }
    
    /**
     * Find all public decks with pagination
     * @param pageable pagination info
     * @return a page of public decks
     */
    public Page<Deck> findPublicDecks(Pageable pageable) {
        return deckRepository.findByIsPublicTrue(pageable);
    }
    
    /**
     * Find all public decks with a specific language pair
     * @param languageFrom the source language
     * @param languageTo the target language
     * @param pageable pagination info
     * @return a page of decks
     */
    public Page<Deck> findPublicDecksByLanguage(
            String languageFrom, String languageTo, Pageable pageable) {
        return deckRepository.findByIsPublicTrueAndLanguageFromAndLanguageTo(
                languageFrom, languageTo, pageable);
    }
    
    /**
     * Search for public decks
     * @param searchTerm the search term
     * @param pageable pagination info
     * @return a page of matching decks
     */
    public Page<Deck> searchPublicDecks(String searchTerm, Pageable pageable) {
        return deckRepository.searchPublicDecks(searchTerm, pageable);
    }
    
    /**
     * Search for decks owned by a specific user
     * @param userId the user ID
     * @param searchTerm the search term
     * @param pageable pagination info
     * @return a page of matching decks
     */
    public Page<Deck> searchUserDecks(UUID userId, String searchTerm, Pageable pageable) {
        return deckRepository.searchUserDecks(userId, searchTerm, pageable);
    }
    
    /**
     * Create a new deck
     * @param deck the deck to create
     * @param userId the owner ID
     * @return the created deck
     */
    @Transactional
    public Deck create(Deck deck, UUID userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        
        deck.setOwner(owner);
        return deckRepository.save(deck);
    }
    
    /**
     * Update a deck
     * @param id the deck ID
     * @param updatedDeck the updated deck data
     * @param userId the user ID (for authorization)
     * @return the updated deck
     */
    @Transactional
    @CacheEvict(value = "decks", key = "#id")
    public Deck update(UUID id, Deck updatedDeck, UUID userId) {
        return deckRepository.findById(id)
                .map(deck -> {
                    // Check if the user is the owner
                    if (!deck.getOwner().getId().equals(userId)) {
                        throw new SecurityException("User not authorized to update this deck");
                    }
                    
                    deck.setName(updatedDeck.getName());
                    deck.setDescription(updatedDeck.getDescription());
                    deck.setPublic(updatedDeck.isPublic());
                    deck.setLanguageFrom(updatedDeck.getLanguageFrom());
                    deck.setLanguageTo(updatedDeck.getLanguageTo());
                    deck.setTags(updatedDeck.getTags());
                    
                    return deckRepository.save(deck);
                })
                .orElseThrow(() -> new IllegalArgumentException("Deck not found with id: " + id));
    }
    
    /**
     * Delete a deck
     * @param id the deck ID
     * @param userId the user ID (for authorization)
     */
    @Transactional
    @CacheEvict(value = "decks", key = "#id")
    public void delete(UUID id, UUID userId) {
        deckRepository.findById(id)
                .ifPresent(deck -> {
                    // Check if the user is the owner
                    if (!deck.getOwner().getId().equals(userId)) {
                        throw new SecurityException("User not authorized to delete this deck");
                    }
                    
                    deckRepository.delete(deck);
                });
    }
    
    /**
     * Check if a user can access a deck
     * @param deckId the deck ID
     * @param userId the user ID
     * @return true if the user can access the deck
     */
    public boolean canUserAccessDeck(UUID deckId, UUID userId) {
        return deckRepository.findById(deckId)
                .map(deck -> deck.isPublic() || deck.getOwner().getId().equals(userId))
                .orElse(false);
    }
}
