package com.devsphere.userservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "profiles")
public class Profile {
    @Id
    private String id;

    private String username;
    private String fullName;
    private String avatarUrl;
    private String bio;
    private List<Skill> skills;
    private int followersCount;
    private int followingCount;
    private Instant createdAt;
}
