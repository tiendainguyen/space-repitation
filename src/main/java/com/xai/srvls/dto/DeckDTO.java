package com.xai.srvls.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Deck entity
 */
@Schema(description = "Deck data transfer object")
public class DeckDTO {
    
    @Schema(description = "Unique identifier of the deck", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
    
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    @Schema(description = "Name of the deck", example = "Spanish Vocabulary", required = true)
    private String name;
    
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Schema(description = "Description of the deck", example = "Basic Spanish vocabulary for beginners")
    private String description;
    
    @Schema(description = "Owner ID of the deck", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID ownerId;
    
    @Schema(description = "Owner username", example = "johndoe")
    private String ownerUsername;
    
    @Schema(description = "Whether the deck is public", example = "true")
    private boolean isPublic;
    
    @Schema(description = "Source language", example = "en")
    private String languageFrom;
    
    @Schema(description = "Target language", example = "es")
    private String languageTo;
    
    @Schema(description = "Tags for the deck", example = "beginner,vocabulary,spanish")
    private String tags;
    
    @Schema(description = "Number of flashcards in the deck", example = "50")
    private int flashcardCount;
    
    @Schema(description = "Creation date and time")
    private LocalDateTime createdAt;
    
    @Schema(description = "Last update date and time")
    private LocalDateTime updatedAt;
    
    // Getters and Setters
    
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public UUID getOwnerId() {
        return ownerId;
    }
    
    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }
    
    public String getOwnerUsername() {
        return ownerUsername;
    }
    
    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }
    
    public boolean isPublic() {
        return isPublic;
    }
    
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
    
    public String getLanguageFrom() {
        return languageFrom;
    }
    
    public void setLanguageFrom(String languageFrom) {
        this.languageFrom = languageFrom;
    }
    
    public String getLanguageTo() {
        return languageTo;
    }
    
    public void setLanguageTo(String languageTo) {
        this.languageTo = languageTo;
    }
    
    public String getTags() {
        return tags;
    }
    
    public void setTags(String tags) {
        this.tags = tags;
    }
    
    public int getFlashcardCount() {
        return flashcardCount;
    }
    
    public void setFlashcardCount(int flashcardCount) {
        this.flashcardCount = flashcardCount;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
