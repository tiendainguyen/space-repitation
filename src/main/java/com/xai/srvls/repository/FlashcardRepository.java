package com.xai.srvls.repository;

import com.xai.srvls.model.Deck;
import com.xai.srvls.model.Flashcard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for Flashcard entity
 */
@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, UUID> {
    
    /**
     * Find all flashcards in a deck
     * @param deck the deck
     * @return a list of flashcards
     */
    List<Flashcard> findByDeck(Deck deck);
    
    /**
     * Find all flashcards in a deck with pagination
     * @param deck the deck
     * @param pageable pagination info
     * @return a page of flashcards
     */
    Page<Flashcard> findByDeck(Deck deck, Pageable pageable);
    
    /**
     * Search for flashcards by content
     * @param deckId the deck ID
     * @param searchTerm the search term
     * @param pageable pagination info
     * @return a page of matching flashcards
     */
    @Query("SELECT f FROM Flashcard f WHERE f.deck.id = :deckId AND " +
           "(LOWER(f.frontContent) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(f.backContent) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(f.tags) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Flashcard> searchFlashcards(
            @Param("deckId") UUID deckId,
            @Param("searchTerm") String searchTerm,
            Pageable pageable);
    
    /**
     * Count flashcards in a deck
     * @param deckId the deck ID
     * @return the number of flashcards
     */
    long countByDeckId(UUID deckId);
    
    /**
     * Find flashcards that need review based on next review date
     * @param userId the user ID
     * @param deckId the deck ID (optional)
     * @param pageable pagination info
     * @return a page of flashcards due for review
     */
    @Query("SELECT f FROM Flashcard f JOIN f.reviews r " +
           "WHERE r.user.id = :userId " +
           "AND (:deckId IS NULL OR f.deck.id = :deckId) " +
           "AND r.nextReviewDate <= CURRENT_TIMESTAMP " +
           "ORDER BY r.nextReviewDate ASC")
    Page<Flashcard> findFlashcardsDueForReview(
            @Param("userId") UUID userId,
            @Param("deckId") UUID deckId,
            Pageable pageable);
}
