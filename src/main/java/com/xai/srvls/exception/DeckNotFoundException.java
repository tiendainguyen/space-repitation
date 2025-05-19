package com.xai.srvls.exception;

import org.aibles.business.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a deck is not found
 */
public class DeckNotFoundException extends BaseException {
    
    /**
     * Constructor for DeckNotFoundException
     *
     * @param deckId the ID of the deck that was not found
     */
    public DeckNotFoundException(String deckId) {
        super(HttpStatus.NOT_FOUND.value(), 
              "com.xai.srvls.exception.DeckNotFoundException", 
              "Deck not found with id: " + deckId);
    }
}
