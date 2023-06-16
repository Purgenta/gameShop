package com.purgenta.gameshop.controllers;

import com.purgenta.gameshop.response.user.UserProfile;
import com.purgenta.gameshop.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    @GetMapping("/profile")
    public ResponseEntity<UserProfile> profile() {
        return userService.getAuthenticatedUser();
    }
    @GetMapping("stats")
    public ResponseEntity<?> stats() {
        return userService.getStats();
    }
}
