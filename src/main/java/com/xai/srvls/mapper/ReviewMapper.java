package com.xai.srvls.mapper;

import com.xai.srvls.dto.ReviewDTO;
import com.xai.srvls.model.Review;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Review entity and ReviewDTO
 */
@Component
public class ReviewMapper {

    /**
     * Convert Review entity to ReviewDTO
     * @param review The Review entity
     * @return The ReviewDTO
     */
    public ReviewDTO toDTO(Review review) {
        if (review == null) {
            return null;
        }
        
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        
        if (review.getFlashcard() != null) {
            dto.setFlashcardId(review.getFlashcard().getId());
        }
        
        dto.setQuality(review.getQuality());
        dto.setEasinessFactor(review.getEasinessFactor());
        dto.setInterval(review.getInterval());
        dto.setRepetitions(review.getRepetitions());
        dto.setReviewDate(review.getReviewDate());
        dto.setNextReviewDate(review.getNextReviewDate());
        dto.setResponseTimeMs(review.getResponseTimeMs());
        
        return dto;
    }

    /**
     * Convert ReviewDTO to Review entity
     * @param dto The ReviewDTO
     * @return The Review entity
     */
    public Review toEntity(ReviewDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Review review = new Review();
        
        // Don't set ID for new entities
        if (dto.getId() != null) {
            review.setId(dto.getId());
        }
        
        review.setQuality(dto.getQuality());
        review.setEasinessFactor(dto.getEasinessFactor());
        review.setInterval(dto.getInterval());
        review.setRepetitions(dto.getRepetitions());
        review.setResponseTimeMs(dto.getResponseTimeMs());
        
        // Flashcard and User are set by the service
        
        return review;
    }
}
