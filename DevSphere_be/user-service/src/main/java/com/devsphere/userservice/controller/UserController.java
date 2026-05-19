package com.devsphere.userservice.controller;

import com.devsphere.userservice.dto.CreateProfileRequest;
import com.devsphere.userservice.dto.ProfileResponse;
import com.devsphere.userservice.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final ProfileService profileService;

    @PostMapping("/me")
   public ProfileResponse createProfile(HttpServletRequest request, @RequestBody CreateProfileRequest body){
        String userId = request.getHeader("X-User-Id");
        return profileService.createProfile(userId, body);
    }

    @GetMapping("/me")
    public ProfileResponse me(
            HttpServletRequest request
    ) {

        String userId =
                request.getHeader("X-User-Id");

        return profileService.getProfile(
                userId
        );
    }
}