package com.xai.srvls.exception;

import org.aibles.business.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when an account is not found by email
 */
public class AccountByEmailNotFoundException extends BaseException {
    
    /**
     * Constructor for AccountByEmailNotFoundException
     *
     * @param email the email that was not found
     */
    public AccountByEmailNotFoundException(String email) {
        super(HttpStatus.NOT_FOUND.value(), 
              "com.xai.srvls.exception.AccountByEmailNotFoundException", 
              "Account not found with email: " + email);
    }
}
