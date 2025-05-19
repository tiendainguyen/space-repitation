package com.xai.srvls.controller;

import com.xai.srvls.dto.ReviewDTO;
import com.xai.srvls.dto.ReviewStatisticsDTO;
import com.xai.srvls.mapper.ReviewMapper;
import com.xai.srvls.model.Review;
import com.xai.srvls.security.CurrentUser;
import com.xai.srvls.security.UserPrincipal;
import com.xai.srvls.service.FlashcardService;
import com.xai.srvls.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * REST controller for Review management
 */
@RestController
@RequestMapping("/reviews")
@Tag(name = "Review Management", description = "APIs for managing flashcard reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final FlashcardService flashcardService;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewController(ReviewService reviewService, FlashcardService flashcardService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.flashcardService = flashcardService;
        this.reviewMapper = reviewMapper;
    }

    /**
     * Submit a flashcard review
     * @param flashcardId Flashcard ID
     * @param reviewDTO Review data
     * @param userPrincipal Current user
     * @return Created review
     */
    @PostMapping("/flashcard/{flashcardId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Submit a flashcard review",
            description = "Records a review for a flashcard with quality assessment",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ReviewDTO> submitReview(
            @PathVariable UUID flashcardId,
            @Valid @RequestBody ReviewDTO reviewDTO,
            @Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal) {

        try {
            Review review = reviewMapper.toEntity(reviewDTO);
            Review createdReview = reviewService.createReview(review, flashcardId, userPrincipal.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(reviewMapper.toDTO(createdReview));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Get reviews for a flashcard
     * @param flashcardId Flashcard ID
     * @param userPrincipal Current user
     * @param pageable Pagination parameters
     * @return Page of reviews
     */
    @GetMapping("/flashcard/{flashcardId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get flashcard reviews",
            description = "Returns all reviews for a specific flashcard by the current user",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<Page<ReviewDTO>> getFlashcardReviews(
            @PathVariable UUID flashcardId,
            @Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal,
            @PageableDefault(size = 20) Pageable pageable) {

        // Check if the user can access the flashcard
        if (!flashcardService.findById(flashcardId)
                .filter(flashcard -> reviewService.canUserAccessFlashcard(flashcard, userPrincipal.getId()))
                .isPresent()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Page<Review> reviews = reviewService.findByFlashcardIdAndUserId(flashcardId, userPrincipal.getId(), pageable);
        return ResponseEntity.ok(reviews.map(reviewMapper::toDTO));
    }

    /**
     * Get review statistics for a user
     * @param startDate Start date
     * @param endDate End date
     * @param deckId Optional deck ID to filter by
     * @param userPrincipal Current user
     * @return Review statistics
     */
    @GetMapping("/statistics")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get review statistics",
            description = "Returns review statistics for the current user",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ReviewStatisticsDTO> getReviewStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) UUID deckId,
            @Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal) {

        ReviewStatisticsDTO statistics = reviewService.getReviewStatistics(
                userPrincipal.getId(), startDate, endDate, deckId);
        
        return ResponseEntity.ok(statistics);
    }

    /**
     * Get reviews for the current user
     * @param userPrincipal Current user
     * @param pageable Pagination parameters
     * @return Page of reviews
     */
    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Get user reviews",
            description = "Returns all reviews by the current user",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<Page<ReviewDTO>> getUserReviews(
            @Parameter(hidden = true) @CurrentUser UserPrincipal userPrincipal,
            @PageableDefault(size = 20) Pageable pageable) {

        Page<Review> reviews = reviewService.findByUserId(userPrincipal.getId(), pageable);
        return ResponseEntity.ok(reviews.map(reviewMapper::toDTO));
    }
}
