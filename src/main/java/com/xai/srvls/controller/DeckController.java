package com.xai.srvls.controller;

import com.xai.srvls.dto.DeckDTO;
import com.xai.srvls.mapper.DeckMapper;
import com.xai.srvls.model.Deck;
import com.xai.srvls.security.CurrentUser;
import com.xai.srvls.security.UserPrincipal;
import com.xai.srvls.service.DeckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
 * REST controller for Deck management
 */
@RestController
@RequestMapping("/decks")
@Tag(name = "Deck Management", description = "APIs for managing flashcard decks")
public class DeckController {
    
    private final DeckService deckService;
    private final DeckMapper deckMapper;
    
    @Autowired
    public DeckController(DeckService deckService, DeckMapper deckMapper) {
        this.deckService = deckService;
        this.deckMapper = deckMapper;
    }
    
    /**
     * Get a deck by ID
     * @param id the deck ID
     * @param userPrincipal the current authenticated user
     * @return the deck if found and accessible
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get a deck by ID",
            description = "Returns a deck if it's public or owned by the current user",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Deck found"),
                    @ApiResponse(responseCode = "404", description = "Deck not found"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    public ResponseEntity<DeckDTO> getDeckById(
            @PathVariable UUID id,
            @Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal) {
        
        return deckService.findById(id)
                .filter(deck -> deck.isPublic() || deck.getOwner().getId().equals(userPrincipal.getId()))
                .map(deckMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get all decks owned by the current user
     * @param userPrincipal the current authenticated user
     * @param pageable pagination parameters
     * @return a page of decks
     */
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get user's decks",
            description = "Returns all decks owned by the current user",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<Page<DeckDTO>> getUserDecks(
            @Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Page<Deck> decks = deckService.findByOwnerId(userPrincipal.getId(), pageable);
        return ResponseEntity.ok(decks.map(deckMapper::toDTO));
    }
    
    /**
     * Get all public decks
     * @param pageable pagination parameters
     * @return a page of public decks
     */
    @GetMapping("/public")
    @Operation(
            summary = "Get public decks",
            description = "Returns all public decks with pagination"
    )
    public ResponseEntity<Page<DeckDTO>> getPublicDecks(
            @RequestParam(required = false) String languageFrom,
            @RequestParam(required = false) String languageTo,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Page<Deck> decks;
        if (languageFrom != null && languageTo != null) {
            decks = deckService.findPublicDecksByLanguage(languageFrom, languageTo, pageable);
        } else {
            decks = deckService.findPublicDecks(pageable);
        }
        
        return ResponseEntity.ok(decks.map(deckMapper::toDTO));
    }
    
    /**
     * Search for decks
     * @param query the search query
     * @param searchPublic whether to search public decks
     * @param userPrincipal the current authenticated user
     * @param pageable pagination parameters
     * @return a page of matching decks
     */
    @GetMapping("/search")
    @Operation(
            summary = "Search decks",
            description = "Search for decks by name, description, or tags"
    )
    public ResponseEntity<Page<DeckDTO>> searchDecks(
            @RequestParam String query,
            @RequestParam(defaultValue = "true") boolean searchPublic,
            @Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Page<Deck> decks;
        if (searchPublic) {
            decks = deckService.searchPublicDecks(query, pageable);
        } else if (userPrincipal != null) {
            decks = deckService.searchUserDecks(userPrincipal.getId(), query, pageable);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        return ResponseEntity.ok(decks.map(deckMapper::toDTO));
    }
    
    /**
     * Create a new deck
     * @param deckDTO the deck to create
     * @param userPrincipal the current authenticated user
     * @return the created deck
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Create a new deck",
            description = "Creates a new deck owned by the current user",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = DeckDTO.class))
            )
    )
    public ResponseEntity<DeckDTO> createDeck(
            @Valid @RequestBody DeckDTO deckDTO,
            @Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal) {
        
        Deck deck = deckMapper.toEntity(deckDTO);
        Deck createdDeck = deckService.create(deck, userPrincipal.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(deckMapper.toDTO(createdDeck));
    }
    
    /**
     * Update a deck
     * @param id the deck ID
     * @param deckDTO the updated deck data
     * @param userPrincipal the current authenticated user
     * @return the updated deck
     */
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Update a deck",
            description = "Updates an existing deck owned by the current user",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Deck updated"),
                    @ApiResponse(responseCode = "404", description = "Deck not found"),
                    @ApiResponse(responseCode = "403", description = "Not authorized")
            }
    )
    public ResponseEntity<DeckDTO> updateDeck(
            @PathVariable UUID id,
            @Valid @RequestBody DeckDTO deckDTO,
            @Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal) {
        
        try {
            Deck deck = deckMapper.toEntity(deckDTO);
            Deck updatedDeck = deckService.update(id, deck, userPrincipal.getId());
            return ResponseEntity.ok(deckMapper.toDTO(updatedDeck));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Delete a deck
     * @param id the deck ID
     * @param userPrincipal the current authenticated user
     * @return no content on success
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Delete a deck",
            description = "Deletes an existing deck owned by the current user",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Deck deleted"),
                    @ApiResponse(responseCode = "403", description = "Not authorized"),
                    @ApiResponse(responseCode = "404", description = "Deck not found")
            }
    )
    public ResponseEntity<Void> deleteDeck(
            @PathVariable UUID id,
            @Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal) {
        
        try {
            deckService.delete(id, userPrincipal.getId());
            return ResponseEntity.noContent().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
