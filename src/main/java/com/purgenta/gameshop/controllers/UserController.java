package com.purgenta.gameshop.controllers;

import com.purgenta.gameshop.models.UserModel;
import com.purgenta.gameshop.services.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserService userService;

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
}
