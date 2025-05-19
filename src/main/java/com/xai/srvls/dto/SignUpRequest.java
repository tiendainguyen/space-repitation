package com.xai.srvls.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for sign up request
 */
@Schema(description = "Sign up request")
public class SignUpRequest {
    
    @NotBlank
    @Size(min = 3, max = 50)
    @Schema(description = "Username", example = "johndoe", required = true)
    private String username;

    @NotBlank
    @Size(max = 100)
    @Email
    @Schema(description = "Email", example = "john.doe@example.com", required = true)
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    @Schema(description = "Password", example = "password123", required = true)
    private String password;
    
    @Schema(description = "First name", example = "John")
    private String firstName;
    
    @Schema(description = "Last name", example = "Doe")
    private String lastName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
