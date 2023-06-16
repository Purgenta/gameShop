package com.purgenta.gameshop.services.user;

import com.purgenta.gameshop.models.user.User;
import com.purgenta.gameshop.response.user.UserProfile;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IUserService {

    ResponseEntity<UserProfile> getAuthenticatedUser();

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
    ResponseEntity<?> getStats();

    Long getUserCount();

}
