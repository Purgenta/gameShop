package com.purgenta.gameshop.controllers;

import com.purgenta.gameshop.models.UserModel;
import com.purgenta.gameshop.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("create")
    @CrossOrigin("*")
    public ResponseEntity<Map<String, String>> index(@RequestBody @Valid UserModel user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("getUsers")
    @CrossOrigin("*")
    public List<UserModel> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("hello")
    @CrossOrigin("*")
    public Map<String, String> hello() {
        Map<String, String> helloJson = new HashMap<>();
        helloJson.put("hello", "Welcome from a secured endpoint");
        return helloJson;
    }
}
