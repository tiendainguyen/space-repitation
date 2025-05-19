package com.xai.srvls.repository;

import com.xai.srvls.model.Deck;
import com.xai.srvls.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for Deck entity
 */
@Repository
public interface DeckRepository extends JpaRepository<Deck, UUID> {
    
    /**
     * Find all decks owned by a user
     * @param owner the owner
     * @return a list of decks
     */
    List<Deck> findByOwner(User owner);
    
    /**
     * Find all decks owned by a user with pagination
     * @param owner the owner
     * @param pageable pagination info
     * @return a page of decks
     */
    Page<Deck> findByOwner(User owner, Pageable pageable);
    
    /**
     * Find all public decks
     * @param pageable pagination info
     * @return a page of public decks
     */
    Page<Deck> findByIsPublicTrue(Pageable pageable);
    
    /**
     * Find all public decks with a specific language pair
     * @param languageFrom the source language
     * @param languageTo the target language
     * @param pageable pagination info
     * @return a page of decks
     */
    Page<Deck> findByIsPublicTrueAndLanguageFromAndLanguageTo(
            String languageFrom, String languageTo, Pageable pageable);
    
    /**
     * Search for decks by name or description
     * @param searchTerm the search term
     * @param pageable pagination info
     * @return a page of matching decks
     */
    @Query("SELECT d FROM Deck d WHERE d.isPublic = true AND " +
           "(LOWER(d.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(d.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(d.tags) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Deck> searchPublicDecks(@Param("searchTerm") String searchTerm, Pageable pageable);
    
    /**
     * Search for decks owned by a specific user
     * @param userId the user ID
     * @param searchTerm the search term
     * @param pageable pagination info
     * @return a page of matching decks
     */
    @Query("SELECT d FROM Deck d WHERE d.owner.id = :userId AND " +
           "(LOWER(d.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(d.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(d.tags) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Deck> searchUserDecks(@Param("userId") UUID userId, 
                              @Param("searchTerm") String searchTerm, 
                              Pageable pageable);
}
