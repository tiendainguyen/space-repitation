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
 * Deck entity representing a collection of flashcards for vocabulary learning
 */
@Entity
@Table(name = "decks")
@EntityListeners(AuditingEntityListener.class)
public class Deck implements Serializable {
    
    @Id
    @GeneratedValue
    private UUID id;
    
    @NotBlank
    @Column(nullable = false)
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    
    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Flashcard> flashcards = new HashSet<>();
    
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    @Column(name = "is_public", nullable = false)
    private boolean isPublic = false;
    
    @Column(name = "language_from")
    private String languageFrom;
    
    @Column(name = "language_to")
    private String languageTo;
    
    @Column(name = "tags")
    private String tags;
    
    // Default constructor
    public Deck() {
    }
    
    // Constructor with required fields
    public Deck(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }
    
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
    
    public User getOwner() {
        return owner;
    }
    
    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    public Set<Flashcard> getFlashcards() {
        return flashcards;
    }
    
    public void setFlashcards(Set<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
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
    
    // Helper methods
    
    public void addFlashcard(Flashcard flashcard) {
        this.flashcards.add(flashcard);
        flashcard.setDeck(this);
    }
    
    public void removeFlashcard(Flashcard flashcard) {
        this.flashcards.remove(flashcard);
        flashcard.setDeck(null);
    }
    
    public int getFlashcardCount() {
        return this.flashcards.size();
    }
}
