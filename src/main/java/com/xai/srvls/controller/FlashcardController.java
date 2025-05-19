package com.xai.srvls.controller;

import com.xai.srvls.dto.FlashcardDTO;
import com.xai.srvls.mapper.FlashcardMapper;
import com.xai.srvls.model.Flashcard;
import com.xai.srvls.security.CurrentUser;
import com.xai.srvls.security.UserPrincipal;
import com.xai.srvls.service.DeckService;
import com.xai.srvls.service.FlashcardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for Flashcard management
 */
@RestController
@RequestMapping("/flashcards")
@Tag(name = "Flashcard Management", description = "APIs for managing flashcards")
public class FlashcardController {

    private final FlashcardService flashcardService;
    private final DeckService deckService;
    private final FlashcardMapper flashcardMapper;

    @Autowired
    public FlashcardController(FlashcardService flashcardService, DeckService deckService, FlashcardMapper flashcardMapper) {
        this.flashcardService = flashcardService;
        this.deckService = deckService;
        this.flashcardMapper = flashcardMapper;
    }

    /**
     * Get a flashcard by ID
     * @param id Flashcard ID
     * @param userPrincipal Current user
     * @return Flashcard if found and accessible
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get a flashcard by ID",
            description = "Returns a flashcard if it's in a public deck or in a deck owned by the current user",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<FlashcardDTO> getFlashcardById(
            @PathVariable UUID id,
            @Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal) {

        return flashcardService.findById(id)
                .filter(flashcard -> {
                    UUID deckId = flashcard.getDeck().getId();
                    return deckService.canUserAccessDeck(deckId, userPrincipal.getId());
                })
                .map(flashcardMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all flashcards in a deck
     * @param deckId Deck ID
     * @param userPrincipal Current user
     * @param pageable Pagination parameters
     * @return Page of flashcards
     */
    @GetMapping("/deck/{deckId}")
    @Operation(
            summary = "Get flashcards in a deck",
            description = "Returns all flashcards in the specified deck if accessible",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<Page<FlashcardDTO>> getFlashcardsByDeck(
            @PathVariable UUID deckId,
            @Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal,
            @PageableDefault(size = 20) Pageable pageable) {

        if (!deckService.canUserAccessDeck(deckId, userPrincipal.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Page<Flashcard> flashcards = flashcardService.findByDeckId(deckId, pageable);
        return ResponseEntity.ok(flashcards.map(flashcardMapper::toDTO));
    }

    /**
     * Search for flashcards in a deck
     * @param deckId Deck ID
     * @param query Search query
     * @param userPrincipal Current user
     * @param pageable Pagination parameters
     * @return Page of matching flashcards
     */
    @GetMapping("/search")
    @Operation(
            summary = "Search flashcards",
            description = "Search for flashcards by content in a specific deck",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<Page<FlashcardDTO>> searchFlashcards(
            @RequestParam UUID deckId,
            @RequestParam String query,
            @Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal,
            @PageableDefault(size = 20) Pageable pageable) {

        if (!deckService.canUserAccessDeck(deckId, userPrincipal.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Page<Flashcard> flashcards = flashcardService.searchFlashcards(deckId, query, pageable);
        return ResponseEntity.ok(flashcards.map(flashcardMapper::toDTO));
    }

    /**
     * Create a new flashcard
     * @param deckId Deck ID
     * @param flashcardDTO Flashcard data
     * @param userPrincipal Current user
     * @return Created flashcard
     */
    @PostMapping("/deck/{deckId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Create a new flashcard",
            description = "Creates a new flashcard in the specified deck",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<FlashcardDTO> createFlashcard(
            @PathVariable UUID deckId,
            @Valid @RequestBody FlashcardDTO flashcardDTO,
            @Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal) {

        try {
            Flashcard flashcard = flashcardMapper.toEntity(flashcardDTO);
            Flashcard createdFlashcard = flashcardService.create(flashcard, deckId, userPrincipal.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(flashcardMapper.toDTO(createdFlashcard));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update a flashcard
     * @param id Flashcard ID
     * @param flashcardDTO Updated flashcard data
     * @param userPrincipal Current user
     * @return Updated flashcard
     */
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Update a flashcard",
            description = "Updates an existing flashcard",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<FlashcardDTO> updateFlashcard(
            @PathVariable UUID id,
            @Valid @RequestBody FlashcardDTO flashcardDTO,
            @Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal) {

        try {
            Flashcard flashcard = flashcardMapper.toEntity(flashcardDTO);
            Flashcard updatedFlashcard = flashcardService.update(id, flashcard, userPrincipal.getId());
            return ResponseEntity.ok(flashcardMapper.toDTO(updatedFlashcard));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a flashcard
     * @param id Flashcard ID
     * @param userPrincipal Current user
     * @return No content on success
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Delete a flashcard",
            description = "Deletes an existing flashcard",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<Void> deleteFlashcard(
            @PathVariable UUID id,
            @Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal) {

        try {
            flashcardService.delete(id, userPrincipal.getId());
            return ResponseEntity.noContent().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get flashcards due for review
     * @param deckId Optional deck ID to filter by
     * @param userPrincipal Current user
     * @param pageable Pagination parameters
     * @return Page of flashcards due for review
     */
    @GetMapping("/review")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get flashcards due for review",
            description = "Returns flashcards that are due for review based on the spaced repetition algorithm",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<Page<FlashcardDTO>> getFlashcardsDueForReview(
            @RequestParam(required = false) UUID deckId,
            @Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal,
            @PageableDefault(size = 20) Pageable pageable) {

        // If deckId is provided, check if user can access the deck
        if (deckId != null && !deckService.canUserAccessDeck(deckId, userPrincipal.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Page<Flashcard> flashcards = flashcardService.findFlashcardsDueForReview(
                userPrincipal.getId(), deckId, pageable);
        
        return ResponseEntity.ok(flashcards.map(flashcardMapper::toDTO));
    }
}
