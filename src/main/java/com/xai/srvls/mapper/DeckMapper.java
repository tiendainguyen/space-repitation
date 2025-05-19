package com.xai.srvls.mapper;

import com.xai.srvls.dto.DeckDTO;
import com.xai.srvls.model.Deck;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Deck entity and DeckDTO
 */
@Component
public class DeckMapper {
    
    /**
     * Convert Deck entity to DeckDTO
     * @param deck the Deck entity
     * @return the DeckDTO
     */
    public DeckDTO toDTO(Deck deck) {
        if (deck == null) {
            return null;
        }
        
        DeckDTO dto = new DeckDTO();
        dto.setId(deck.getId());
        dto.setName(deck.getName());
        dto.setDescription(deck.getDescription());
        
        if (deck.getOwner() != null) {
            dto.setOwnerId(deck.getOwner().getId());
            dto.setOwnerUsername(deck.getOwner().getUsername());
        }
        
        dto.setPublic(deck.isPublic());
        dto.setLanguageFrom(deck.getLanguageFrom());
        dto.setLanguageTo(deck.getLanguageTo());
        dto.setTags(deck.getTags());
        dto.setFlashcardCount(deck.getFlashcards().size());
        dto.setCreatedAt(deck.getCreatedAt());
        dto.setUpdatedAt(deck.getUpdatedAt());
        
        return dto;
    }
    
    /**
     * Convert DeckDTO to Deck entity
     * @param dto the DeckDTO
     * @return the Deck entity
     */
    public Deck toEntity(DeckDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Deck deck = new Deck();
        
        // Don't set ID for new entities
        if (dto.getId() != null) {
            deck.setId(dto.getId());
        }
        
        deck.setName(dto.getName());
        deck.setDescription(dto.getDescription());
        deck.setPublic(dto.isPublic());
        deck.setLanguageFrom(dto.getLanguageFrom());
        deck.setLanguageTo(dto.getLanguageTo());
        deck.setTags(dto.getTags());
        
        // Owner is set by the service
        
        return deck;
    }
}
