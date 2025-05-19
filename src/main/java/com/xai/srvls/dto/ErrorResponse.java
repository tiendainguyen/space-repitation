package com.xai.srvls.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.sql.Timestamp;
import java.util.Map;

/**
 * DTO for standardized error responses
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from JSON
public class ErrorResponse {
    private Timestamp timestamp;
    private int status;
    private String code;
    private String message;
    private Map<String, String> errors;

    /**
     * Constructor for ErrorResponse
     *
     * @param timestamp the timestamp of the error
     * @param status the HTTP status code
     * @param code the error code
     * @param message the error message
     */
    public ErrorResponse(Timestamp timestamp, int status, String code, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
