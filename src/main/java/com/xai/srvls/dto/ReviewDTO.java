package com.xai.srvls.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Review entity
 */
@Schema(description = "Review data transfer object")
public class ReviewDTO {
    
    @Schema(description = "Unique identifier of the review", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
    
    @Schema(description = "ID of the flashcard being reviewed", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID flashcardId;
    
    @Min(value = 0, message = "Quality must be between 0 and 5")
    @Max(value = 5, message = "Quality must be between 0 and 5")
    @Schema(description = "Quality of the response (0-5)", example = "4", required = true)
    private int quality;
    
    @Schema(description = "Easiness factor", example = "2.5")
    private double easinessFactor;
    
    @Schema(description = "Interval in days", example = "7")
    private int interval;
    
    @Schema(description = "Number of repetitions", example = "3")
    private int repetitions;
    
    @Schema(description = "Review date")
    private LocalDateTime reviewDate;
    
    @Schema(description = "Next review date")
    private LocalDateTime nextReviewDate;
    
    @Schema(description = "Response time in milliseconds", example = "3500")
    private Long responseTimeMs;

    // Getters and Setters
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getFlashcardId() {
        return flashcardId;
    }

    public void setFlashcardId(UUID flashcardId) {
        this.flashcardId = flashcardId;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
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
}
