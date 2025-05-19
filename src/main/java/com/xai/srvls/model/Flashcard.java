package com.xai.srvls.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Flashcard entity representing a vocabulary card with front and back content
 */
@Entity
@Table(name = "flashcards")
@EntityListeners(AuditingEntityListener.class)
public class Flashcard implements Serializable {
    
    @Id
    @GeneratedValue
    private UUID id;
    
    @NotBlank
    @Column(name = "front_content", nullable = false)
    private String frontContent;
    
    @NotBlank
    @Column(name = "back_content", nullable = false)
    private String backContent;
    
    @Column(name = "front_image_url")
    private String frontImageUrl;
    
    @Column(name = "back_image_url")
    private String backImageUrl;
    
    @Column(name = "front_audio_url")
    private String frontAudioUrl;
    
    @Column(name = "back_audio_url")
    private String backAudioUrl;
    
    @Column(name = "hint")
    private String hint;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;
    
    @OneToMany(mappedBy = "flashcard", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();
    
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    @Column(name = "tags")
    private String tags;
    
    // Default constructor
    public Flashcard() {
    }
    
    // Constructor with required fields
    public Flashcard(String frontContent, String backContent, Deck deck) {
        this.frontContent = frontContent;
        this.backContent = backContent;
        this.deck = deck;
    }
    
    // Getters and Setters
    
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getFrontContent() {
        return frontContent;
    }
    
    public void setFrontContent(String frontContent) {
        this.frontContent = frontContent;
    }
    
    public String getBackContent() {
        return backContent;
    }
    
    public void setBackContent(String backContent) {
        this.backContent = backContent;
    }
    
    public String getFrontImageUrl() {
        return frontImageUrl;
    }
    
    public void setFrontImageUrl(String frontImageUrl) {
        this.frontImageUrl = frontImageUrl;
    }
    
    public String getBackImageUrl() {
        return backImageUrl;
    }
    
    public void setBackImageUrl(String backImageUrl) {
        this.backImageUrl = backImageUrl;
    }
    
    public String getFrontAudioUrl() {
        return frontAudioUrl;
    }
    
    public void setFrontAudioUrl(String frontAudioUrl) {
        this.frontAudioUrl = frontAudioUrl;
    }
    
    public String getBackAudioUrl() {
        return backAudioUrl;
    }
    
    public void setBackAudioUrl(String backAudioUrl) {
        this.backAudioUrl = backAudioUrl;
    }
    
    public String getHint() {
        return hint;
    }
    
    public void setHint(String hint) {
        this.hint = hint;
    }
    
    public Deck getDeck() {
        return deck;
    }
    
    public void setDeck(Deck deck) {
        this.deck = deck;
    }
    
    public Set<Review> getReviews() {
        return reviews;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public String getTags() {
        return tags;
    }
    
    public void setTags(String tags) {
        this.tags = tags;
    }
    
    // Helper methods
    
    public void addReview(Review review) {
        this.reviews.add(review);
        review.setFlashcard(this);
    }
    
    public void removeReview(Review review) {
        this.reviews.remove(review);
        review.setFlashcard(null);
    }
    
    /**
     * Calculate the next review date based on the SM-2 spaced repetition algorithm
     * @param quality The quality of the user's response (0-5)
     * @param user The user who performed the review
     * @return The next review date
     */
    public LocalDateTime calculateNextReviewDate(int quality, User user) {
        // Implementation of SM-2 algorithm would go here
        // For now, return a simple example based on quality
        int daysToAdd = switch (quality) {
            case 0, 1 -> 1;  // Review again tomorrow
            case 2 -> 3;     // Review in 3 days
            case 3 -> 7;     // Review in a week
            case 4 -> 14;    // Review in 2 weeks
            case 5 -> 30;    // Review in a month
            default -> 1;    // Default to tomorrow
        };
        
        return LocalDateTime.now().plusDays(daysToAdd);
    }
}
