package com.xai.srvls.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for login request
 */
@Schema(description = "Login request")
public class LoginRequest {
    
    @NotBlank
    @Schema(description = "Username or email", example = "johndoe", required = true)
    private String usernameOrEmail;

    @NotBlank
    @Schema(description = "Password", example = "password123", required = true)
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
