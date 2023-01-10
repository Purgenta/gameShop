package com.purgenta.gameshop.controllers;

import com.purgenta.gameshop.models.User;
import com.purgenta.gameshop.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    @GetMapping("/profile")
    public User profile() {
        return userService.getAuthenticatedUser();
    }
}
