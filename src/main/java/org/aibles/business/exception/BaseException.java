package org.aibles.business.exception;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Base exception class for all application exceptions
 */
public class BaseException extends RuntimeException {
    private final int status;
    private final String code;
    private final Timestamp timestamp;
    private final String message;

    /**
     * Constructor for BaseException
     *
     * @param status HTTP status code
     * @param code Error code
     * @param message Error message
     */
    public BaseException(int status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.message = message;
        this.timestamp = Timestamp.from(Instant.now());
    }

    /**
     * Get HTTP status code
     *
     * @return status code
     */
    public int getStatus() {
        return status;
    }

    /**
     * Get error code
     *
     * @return error code
     */
    public String getCode() {
        return code;
    }

    /**
     * Get timestamp
     *
     * @return timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Get error message
     *
     * @return error message
     */
    @Override
    public String getMessage() {
        return message;
    }
}
