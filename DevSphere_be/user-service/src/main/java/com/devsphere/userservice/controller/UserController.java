package com.devsphere.userservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/test")
    public String test() {
        return "User service working";
    }

    @GetMapping("/me")
    public String me(
            HttpServletRequest request
    ) {

        String userId =
                request.getHeader("X-User-Id");

        return "Current user: " + userId;
    }
}