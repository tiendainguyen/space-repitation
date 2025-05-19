package com.xai.srvls.exception;

import org.aibles.business.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a role is not found
 */
public class RoleNotFoundException extends BaseException {
    
    /**
     * Constructor for RoleNotFoundException
     *
     * @param roleName the name of the role that was not found
     */
    public RoleNotFoundException(String roleName) {
        super(HttpStatus.NOT_FOUND.value(), 
              "com.xai.srvls.exception.RoleNotFoundException", 
              "Role not found: " + roleName);
    }
}
