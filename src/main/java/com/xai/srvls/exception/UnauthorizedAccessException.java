package com.xai.srvls.exception;

import org.aibles.business.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a user is not authorized to perform an action
 */
public class UnauthorizedAccessException extends BaseException {
    
    /**
     * Constructor for UnauthorizedAccessException
     *
     * @param resource the resource that was attempted to be accessed
     */
    public UnauthorizedAccessException(String resource) {
        super(HttpStatus.FORBIDDEN.value(), 
              "com.xai.srvls.exception.UnauthorizedAccessException", 
              "User not authorized to access " + resource);
    }
}
