package com.xai.srvls.exception;

import org.aibles.business.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a flashcard is not found
 */
public class FlashcardNotFoundException extends BaseException {
    
    /**
     * Constructor for FlashcardNotFoundException
     *
     * @param flashcardId the ID of the flashcard that was not found
     */
    public FlashcardNotFoundException(String flashcardId) {
        super(HttpStatus.NOT_FOUND.value(), 
              "com.xai.srvls.exception.FlashcardNotFoundException", 
              "Flashcard not found with id: " + flashcardId);
    }
}
