package com.xai.srvls.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Flashcard entity
 */
@Schema(description = "Flashcard data transfer object")
public class FlashcardDTO {
    
    @Schema(description = "Unique identifier of the flashcard", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
    
    @NotBlank(message = "Front content is required")
    @Size(max = 1000, message = "Front content cannot exceed 1000 characters")
    @Schema(description = "Content on the front of the flashcard", example = "Hola", required = true)
    private String frontContent;
    
    @NotBlank(message = "Back content is required")
    @Size(max = 1000, message = "Back content cannot exceed 1000 characters")
    @Schema(description = "Content on the back of the flashcard", example = "Hello", required = true)
    private String backContent;
    
    @Schema(description = "URL for the front image")
    private String frontImageUrl;
    
    @Schema(description = "URL for the back image")
    private String backImageUrl;
    
    @Schema(description = "URL for the front audio")
    private String frontAudioUrl;
    
    @Schema(description = "URL for the back audio")
    private String backAudioUrl;
    
    @Schema(description = "Hint for the flashcard", example = "Greeting in Spanish")
    private String hint;
    
    @Schema(description = "ID of the deck this flashcard belongs to", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID deckId;
    
    @Schema(description = "Tags for the flashcard", example = "greeting,basic,spanish")
    private String tags;
    
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

    public UUID getDeckId() {
        return deckId;
    }

    public void setDeckId(UUID deckId) {
        this.deckId = deckId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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
