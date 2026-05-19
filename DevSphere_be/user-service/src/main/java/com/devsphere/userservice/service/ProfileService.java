package com.devsphere.userservice.service;

import com.devsphere.userservice.dto.CreateProfileRequest;
import com.devsphere.userservice.dto.ProfileResponse;
import com.devsphere.userservice.model.Profile;
import com.devsphere.userservice.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

    public ProfileResponse createProfile(String userId, CreateProfileRequest request) {
        Profile profile = Profile.builder()
                .id(userId)
                .username(request.getUsername())
                .fullName(request.getFullName())
                .bio(request.getBio())
                .followersCount(0)
                .followingCount(0)
                .createdAt(Instant.now())
                .build();

        profileRepository.save(profile);
        return map(profile);
    }

    public ProfileResponse getProfile(String userId) {
        Profile profile = profileRepository.findById(userId).orElseThrow();

        return map(profile);
    }

    private ProfileResponse map(Profile profile) {
        return ProfileResponse.builder()
                .id(profile.getId())
                .username(profile.getUsername())
                .fullName(profile.getFullName())
                .bio(profile.getBio())
                .followersCount(profile.getFollowersCount())
                .followingCount(profile.getFollowingCount())
                .build();
    }
}
