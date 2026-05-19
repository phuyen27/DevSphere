package com.devsphere.authservice.entity;

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
@Document("users")
public class User {
    @Id
    private String id;

    private String email;
    private String passwordHash;
    private List<String> roles;
    private Instant createdAt;
    private String avatarUrl;
    private String userName;
}
