package com.xai.srvls.repository;

import com.xai.srvls.model.Flashcard;
import com.xai.srvls.model.Review;
import com.xai.srvls.model.User;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Review entity
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    
    /**
     * Find all reviews for a user
     * @param user the user
     * @param pageable pagination info
     * @return a page of reviews
     */
    Page<Review> findByUser(User user, Pageable pageable);
    
    /**
     * Find all reviews for a flashcard
     * @param flashcard the flashcard
     * @param pageable pagination info
     * @return a page of reviews
     */
    Page<Review> findByFlashcard(Flashcard flashcard, Pageable pageable);
    
    /**
     * Find the most recent review for a flashcard by a specific user
     * @param flashcardId the flashcard ID
     * @param userId the user ID
     * @return an Optional containing the review if found
     */
    Optional<Review> findTopByFlashcardIdAndUserIdOrderByReviewDateDesc(
            UUID flashcardId, UUID userId);
    
    /**
     * Find all flashcards due for review for a user
     * @param userId the user ID
     * @param deckId the deck ID (optional)
     * @return a list of flashcard IDs
     */
    @Query("SELECT r.flashcard.id FROM Review r " +
           "WHERE r.user.id = :userId " +
           "AND (:deckId IS NULL OR r.flashcard.deck.id = :deckId) " +
           "AND r.nextReviewDate <= :currentDateTime " +
           "ORDER BY r.nextReviewDate ASC")
    List<UUID> findFlashcardIdsDueForReview(
            @Param("userId") UUID userId,
            @Param("deckId") UUID deckId,
            @Param("currentDateTime") LocalDateTime currentDateTime);
    
    /**
     * Count the number of reviews for a flashcard by a specific user
     * @param flashcardId the flashcard ID
     * @param userId the user ID
     * @return the count of reviews
     */
    long countByFlashcardIdAndUserId(UUID flashcardId, UUID userId);
    
    /**
     * Get review statistics for a user
     * @param userId the user ID
     * @param startDate the start date
     * @param endDate the end date
     * @return a list of daily review counts
     */
    @Query("SELECT CAST(r.reviewDate AS date) as reviewDay, COUNT(r) as reviewCount " +
           "FROM Review r " +
           "WHERE r.user.id = :userId " +
           "AND r.reviewDate BETWEEN :startDate AND :endDate " +
           "GROUP BY CAST(r.reviewDate AS date) " +
           "ORDER BY reviewDay")
    List<Object[]> getReviewStatsByDateRange(
            @Param("userId") UUID userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    Page<Review> findByFlashcardIdAndUserId(UUID flashcardId, UUID userId, Pageable pageable);

    List<Review> findByUserIdAndReviewDateBetween(UUID userId, LocalDateTime startDate, LocalDateTime endDate);
}
