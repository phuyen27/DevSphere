package com.devsphere.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @Email(message = "Email invalid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be >= 6 character")
    private String password;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username 3-20 chars")
    private String username;

    @Pattern(
            regexp = "^(https?://.*\\.(png|jpg|jpeg|gif))?$",
            message = "Invalid avatar URL"
    )
    private String avatarUrl;
}
