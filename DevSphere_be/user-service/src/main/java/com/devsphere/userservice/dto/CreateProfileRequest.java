package com.devsphere.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateProfileRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3,max = 50, message = "Username must be 3-50 characters")
    private String username;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Size(max = 500, message = "Bio must not exceed 500 characters")
    private String bio;
}
