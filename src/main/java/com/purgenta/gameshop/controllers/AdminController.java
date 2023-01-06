package com.purgenta.gameshop.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/hello")
    @CrossOrigin("*")
    public Map<String, String> sayHelloToAdmin() {
        Map<String, String> helloAdmin = new HashMap<>();
        helloAdmin.put("hello", "Hello to our website admin");
        return helloAdmin;
    }
}
