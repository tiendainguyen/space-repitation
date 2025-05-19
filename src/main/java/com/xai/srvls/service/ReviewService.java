package com.xai.srvls.service;

import com.xai.srvls.dto.ReviewStatisticsDTO;
import com.xai.srvls.model.Flashcard;
import com.xai.srvls.model.Review;
import com.xai.srvls.model.User;
import com.xai.srvls.repository.FlashcardRepository;
import com.xai.srvls.repository.ReviewRepository;
import com.xai.srvls.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for Review-related operations
 */
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final FlashcardRepository flashcardRepository;
    private final UserRepository userRepository;
    private final DeckService deckService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, FlashcardRepository flashcardRepository,
                         UserRepository userRepository, DeckService deckService) {
        this.reviewRepository = reviewRepository;
        this.flashcardRepository = flashcardRepository;
        this.userRepository = userRepository;
        this.deckService = deckService;
    }

    /**
     * Find a review by ID
     * @param id The review ID
     * @return An Optional containing the review if found
     */
    public Optional<Review> findById(UUID id) {
        return reviewRepository.findById(id);
    }

    /**
     * Find all reviews by a user
     * @param userId The user ID
     * @param pageable Pagination information
     * @return A page of reviews
     */
    public Page<Review> findByUserId(UUID userId, Pageable pageable) {
        return userRepository.findById(userId)
                .map(user -> reviewRepository.findByUser(user, pageable))
                .orElse(Page.empty());
    }

    /**
     * Find all reviews for a flashcard by a user
     * @param flashcardId The flashcard ID
     * @param userId The user ID
     * @param pageable Pagination information
     * @return A page of reviews
     */
    public Page<Review> findByFlashcardIdAndUserId(UUID flashcardId, UUID userId, Pageable pageable) {
        return reviewRepository.findByFlashcardIdAndUserId(flashcardId, userId, pageable);
    }

    /**
     * Create a review for a flashcard
     * @param review The review to create
     * @param flashcardId The flashcard ID
     * @param userId The user ID
     * @return The created review
     */
    @Transactional
    public Review createReview(Review review, UUID flashcardId, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        
        Flashcard flashcard = flashcardRepository.findById(flashcardId)
                .orElseThrow(() -> new IllegalArgumentException("Flashcard not found with id: " + flashcardId));
        
        // Check if the user can access the flashcard
        if (!canUserAccessFlashcard(flashcard, userId)) {
            throw new SecurityException("User not authorized to review this flashcard");
        }
        
        review.setUser(user);
        review.setFlashcard(flashcard);
        review.setReviewDate(LocalDateTime.now());
        
        // If the review doesn't have pre-set values, calculate them based on the SM-2 algorithm
        if (review.getEasinessFactor() == 0) {
            Optional<Review> previousReview = reviewRepository
                    .findTopByFlashcardIdAndUserIdOrderByReviewDateDesc(flashcardId, userId);
            
            if (previousReview.isPresent()) {
                Review prev = previousReview.get();
                review.setEasinessFactor(prev.getEasinessFactor());
                review.setRepetitions(prev.getRepetitions());
            } else {
                // Default values for first review
                review.setEasinessFactor(2.5);
                review.setRepetitions(0);
            }
            
            // Update based on quality
            updateSpacedRepetitionParams(review);
        }
        
        return reviewRepository.save(review);
    }

    /**
     * Update spaced repetition parameters for a review
     * @param review The review to update
     */
    private void updateSpacedRepetitionParams(Review review) {
        int quality = review.getQuality();
        
        // Implementation of SM-2 algorithm
        if (quality < 3) {
            // If response quality is less than 3, reset repetitions
            review.setRepetitions(0);
            review.setInterval(1);
        } else {
            // If quality is 3 or higher, increase repetitions count
            review.setRepetitions(review.getRepetitions() + 1);
            
            // Calculate new interval based on repetitions
            if (review.getRepetitions() == 1) {
                review.setInterval(1);
            } else if (review.getRepetitions() == 2) {
                review.setInterval(6);
            } else {
                review.setInterval((int) Math.round(review.getInterval() * review.getEasinessFactor()));
            }
        }
        
        // Update easiness factor
        double newEF = review.getEasinessFactor() + (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02));
        review.setEasinessFactor(Math.max(1.3, newEF));
        
        // Set next review date
        review.setNextReviewDate(review.getReviewDate().plusDays(review.getInterval()));
    }

    /**
     * Check if a user can access a flashcard
     * @param flashcard The flashcard
     * @param userId The user ID
     * @return true if the user can access the flashcard
     */
    public boolean canUserAccessFlashcard(Flashcard flashcard, UUID userId) {
        return deckService.canUserAccessDeck(flashcard.getDeck().getId(), userId);
    }

    /**
     * Get review statistics for a user
     * @param userId The user ID
     * @param startDate The start date
     * @param endDate The end date
     * @param deckId Optional deck ID to filter by
     * @return Review statistics
     */
    public ReviewStatisticsDTO getReviewStatistics(UUID userId, LocalDate startDate, LocalDate endDate, UUID deckId) {
        // Convert dates to datetime for repository query
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        
        // Get all reviews in the date range
        List<Review> reviews = reviewRepository.findByUserIdAndReviewDateBetween(userId, startDateTime, endDateTime);
        
        // Filter by deck if specified
        if (deckId != null) {
            reviews = reviews.stream()
                    .filter(r -> r.getFlashcard().getDeck().getId().equals(deckId))
                    .collect(Collectors.toList());
        }
        
        // Create statistics DTO
        ReviewStatisticsDTO statistics = new ReviewStatisticsDTO();
        
        // Total reviews
        statistics.setTotalReviews(reviews.size());
        
        // Unique flashcards reviewed
        long uniqueFlashcards = reviews.stream()
                .map(r -> r.getFlashcard().getId())
                .distinct()
                .count();
        statistics.setUniqueFlashcardsReviewed((int) uniqueFlashcards);
        
        // Average quality
        double avgQuality = reviews.stream()
                .mapToInt(Review::getQuality)
                .average()
                .orElse(0.0);
        statistics.setAverageQuality(avgQuality);
        
        // Daily review counts
        Map<LocalDate, Integer> dailyCounts = new HashMap<>();
        for (Review review : reviews) {
            LocalDate date = review.getReviewDate().toLocalDate();
            dailyCounts.put(date, dailyCounts.getOrDefault(date, 0) + 1);
        }
        statistics.setDailyReviewCounts(dailyCounts);
        
        // Quality distribution
        Map<Integer, Integer> qualityDist = new HashMap<>();
        for (int i = 0; i <= 5; i++) {
            qualityDist.put(i, 0);
        }
        for (Review review : reviews) {
            int quality = review.getQuality();
            qualityDist.put(quality, qualityDist.getOrDefault(quality, 0) + 1);
        }
        statistics.setQualityDistribution(qualityDist);
        
        // Average response time
        long avgResponseTime = (long) reviews.stream()
                .filter(r -> r.getResponseTimeMs() != null)
                .mapToLong(Review::getResponseTimeMs)
                .average()
                .orElse(0.0);
        statistics.setAverageResponseTimeMs((long) avgResponseTime);
        
        // Mastery percentage (quality 4-5)
        long masterCount = reviews.stream()
                .filter(r -> r.getQuality() >= 4)
                .count();
        double masteryPercentage = reviews.isEmpty() ? 0.0 : (double) masterCount / reviews.size() * 100;
        statistics.setMasteryPercentage(masteryPercentage);
        
        return statistics;
    }
}
