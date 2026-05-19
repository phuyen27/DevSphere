package com.devsphere.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponse {
    private String id;
    private String username;
    private String fullName;
    private String bio;
    private int followersCount;
    private int followingCount;
}
