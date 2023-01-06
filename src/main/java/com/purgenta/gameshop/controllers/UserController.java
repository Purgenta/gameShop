package com.purgenta.gameshop.controllers;

import com.purgenta.gameshop.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/hello")
    @CrossOrigin("*")
    public List<String> hello() {
        return List.of("one", "two");
    }
}
