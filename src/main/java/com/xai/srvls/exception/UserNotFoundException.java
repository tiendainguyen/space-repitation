package com.xai.srvls.exception;

import org.aibles.business.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a user is not found by ID
 */
public class UserNotFoundException extends BaseException {
    
    /**
     * Constructor for UserNotFoundException with user ID
     *
     * @param userId the ID of the user that was not found
     */
    public UserNotFoundException(String userId) {
        super(HttpStatus.NOT_FOUND.value(), 
              "com.xai.srvls.exception.UserNotFoundException", 
              "User not found with id: " + userId);
    }
}
