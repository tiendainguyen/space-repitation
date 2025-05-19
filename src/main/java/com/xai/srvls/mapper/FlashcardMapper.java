package com.xai.srvls.mapper;

import com.xai.srvls.dto.FlashcardDTO;
import com.xai.srvls.model.Flashcard;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Flashcard entity and FlashcardDTO
 */
@Component
public class FlashcardMapper {

    /**
     * Convert Flashcard entity to FlashcardDTO
     * @param flashcard The Flashcard entity
     * @return The FlashcardDTO
     */
    public FlashcardDTO toDTO(Flashcard flashcard) {
        if (flashcard == null) {
            return null;
        }
        
        FlashcardDTO dto = new FlashcardDTO();
        dto.setId(flashcard.getId());
        dto.setFrontContent(flashcard.getFrontContent());
        dto.setBackContent(flashcard.getBackContent());
        dto.setFrontImageUrl(flashcard.getFrontImageUrl());
        dto.setBackImageUrl(flashcard.getBackImageUrl());
        dto.setFrontAudioUrl(flashcard.getFrontAudioUrl());
        dto.setBackAudioUrl(flashcard.getBackAudioUrl());
        dto.setHint(flashcard.getHint());
        
        if (flashcard.getDeck() != null) {
            dto.setDeckId(flashcard.getDeck().getId());
        }
        
        dto.setTags(flashcard.getTags());
        dto.setCreatedAt(flashcard.getCreatedAt());
        dto.setUpdatedAt(flashcard.getUpdatedAt());
        
        return dto;
    }

    /**
     * Convert FlashcardDTO to Flashcard entity
     * @param dto The FlashcardDTO
     * @return The Flashcard entity
     */
    public Flashcard toEntity(FlashcardDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Flashcard flashcard = new Flashcard();
        
        // Don't set ID for new entities
        if (dto.getId() != null) {
            flashcard.setId(dto.getId());
        }
        
        flashcard.setFrontContent(dto.getFrontContent());
        flashcard.setBackContent(dto.getBackContent());
        flashcard.setFrontImageUrl(dto.getFrontImageUrl());
        flashcard.setBackImageUrl(dto.getBackImageUrl());
        flashcard.setFrontAudioUrl(dto.getFrontAudioUrl());
        flashcard.setBackAudioUrl(dto.getBackAudioUrl());
        flashcard.setHint(dto.getHint());
        flashcard.setTags(dto.getTags());
        
        // Deck is set by the service
        
        return flashcard;
    }
}
