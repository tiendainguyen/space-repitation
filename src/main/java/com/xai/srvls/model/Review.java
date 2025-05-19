package com.xai.srvls.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Review entity representing a single review session for a flashcard
 */
@Entity
@Table(name = "reviews")
@EntityListeners(AuditingEntityListener.class)
public class Review implements Serializable {
    
    @Id
    @GeneratedValue
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flashcard_id", nullable = false)
    private Flashcard flashcard;
    
    @Column(name = "quality", nullable = false)
    private int quality; // 0-5 scale as per SM-2 algorithm
    
    @Column(name = "easiness_factor", nullable = false)
    private double easinessFactor = 2.5; // Default easiness factor
    
    @Column(name = "interval", nullable = false)
    private int interval = 1; // Interval in days
    
    @Column(name = "repetitions", nullable = false)
    private int repetitions = 0; // Number of successful reviews
    
    @Column(name = "review_date", nullable = false)
    @CreatedDate
    private LocalDateTime reviewDate;
    
    @Column(name = "next_review_date", nullable = false)
    private LocalDateTime nextReviewDate;
    
    @Column(name = "response_time_ms")
    private Long responseTimeMs;
    
    // Default constructor
    public Review() {
    }
    
    // Constructor with required fields
    public Review(User user, Flashcard flashcard, int quality) {
        this.user = user;
        this.flashcard = flashcard;
        this.quality = quality;
        this.reviewDate = LocalDateTime.now();
        updateSpacedRepetitionParams();
    }
    
    // Getters and Setters
    
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Flashcard getFlashcard() {
        return flashcard;
    }
    
    public void setFlashcard(Flashcard flashcard) {
        this.flashcard = flashcard;
    }
    
    public int getQuality() {
        return quality;
    }
    
    public void setQuality(int quality) {
        this.quality = quality;
        updateSpacedRepetitionParams();
    }
    
    public double getEasinessFactor() {
        return easinessFactor;
    }
    
    public void setEasinessFactor(double easinessFactor) {
        this.easinessFactor = easinessFactor;
    }
    
    public int getInterval() {
        return interval;
    }
    
    public void setInterval(int interval) {
        this.interval = interval;
    }
    
    public int getRepetitions() {
        return repetitions;
    }
    
    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }
    
    public LocalDateTime getReviewDate() {
        return reviewDate;
    }
    
    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }
    
    public LocalDateTime getNextReviewDate() {
        return nextReviewDate;
    }
    
    public void setNextReviewDate(LocalDateTime nextReviewDate) {
        this.nextReviewDate = nextReviewDate;
    }
    
    public Long getResponseTimeMs() {
        return responseTimeMs;
    }
    
    public void setResponseTimeMs(Long responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
    }
    
    /**
     * Update spaced repetition parameters based on SM-2 algorithm
     */
    private void updateSpacedRepetitionParams() {
        // Implementation of SM-2 algorithm
        if (quality < 3) {
            // If response quality is less than 3, reset repetitions
            repetitions = 0;
            interval = 1;
        } else {
            // If quality is 3 or higher, increase repetitions count
            repetitions++;
            
            // Calculate new interval based on repetitions
            if (repetitions == 1) {
                interval = 1;
            } else if (repetitions == 2) {
                interval = 6;
            } else {
                interval = (int) Math.round(interval * easinessFactor);
            }
        }
        
        // Update easiness factor
        easinessFactor = Math.max(1.3, easinessFactor + (0.1 - (5 - quality) * (0.08 + (5 - quality) * 0.02)));
        
        // Set next review date
        nextReviewDate = reviewDate.plusDays(interval);
    }
}
