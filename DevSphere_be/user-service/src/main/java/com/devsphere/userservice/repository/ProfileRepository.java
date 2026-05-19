package com.devsphere.userservice.repository;

import com.devsphere.userservice.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProfileRepository extends MongoRepository<Profile,String> {
    Optional<Profile> findByUsername(String username);
}
