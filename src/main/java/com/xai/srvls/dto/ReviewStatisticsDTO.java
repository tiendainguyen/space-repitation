package com.xai.srvls.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.Map;

/**
 * Data Transfer Object for Review statistics
 */
@Schema(description = "Review statistics data transfer object")
public class ReviewStatisticsDTO {
    
    @Schema(description = "Total number of reviews", example = "240")
    private int totalReviews;
    
    @Schema(description = "Total number of unique flashcards reviewed", example = "120")
    private int uniqueFlashcardsReviewed;
    
    @Schema(description = "Average quality score", example = "3.7")
    private double averageQuality;
    
    @Schema(description = "Daily review counts mapped by date", 
            example = "{\"2023-01-01\": 12, \"2023-01-02\": 18}")
    private Map<LocalDate, Integer> dailyReviewCounts;
    
    @Schema(description = "Distribution of quality scores", 
            example = "{\"0\": 5, \"1\": 10, \"2\": 20, \"3\": 50, \"4\": 100, \"5\": 55}")
    private Map<Integer, Integer> qualityDistribution;
    
    @Schema(description = "Average response time in milliseconds", example = "3200")
    private long averageResponseTimeMs;
    
    @Schema(description = "Mastery percentage (flashcards with quality 4-5)", example = "65.5")
    private double masteryPercentage;

    // Getters and Setters
    
    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public int getUniqueFlashcardsReviewed() {
        return uniqueFlashcardsReviewed;
    }

    public void setUniqueFlashcardsReviewed(int uniqueFlashcardsReviewed) {
        this.uniqueFlashcardsReviewed = uniqueFlashcardsReviewed;
    }

    public double getAverageQuality() {
        return averageQuality;
    }

    public void setAverageQuality(double averageQuality) {
        this.averageQuality = averageQuality;
    }

    public Map<LocalDate, Integer> getDailyReviewCounts() {
        return dailyReviewCounts;
    }

    public void setDailyReviewCounts(Map<LocalDate, Integer> dailyReviewCounts) {
        this.dailyReviewCounts = dailyReviewCounts;
    }

    public Map<Integer, Integer> getQualityDistribution() {
        return qualityDistribution;
    }

    public void setQualityDistribution(Map<Integer, Integer> qualityDistribution) {
        this.qualityDistribution = qualityDistribution;
    }

    public long getAverageResponseTimeMs() {
        return averageResponseTimeMs;
    }

    public void setAverageResponseTimeMs(long averageResponseTimeMs) {
        this.averageResponseTimeMs = averageResponseTimeMs;
    }

    public double getMasteryPercentage() {
        return masteryPercentage;
    }

    public void setMasteryPercentage(double masteryPercentage) {
        this.masteryPercentage = masteryPercentage;
    }
}
