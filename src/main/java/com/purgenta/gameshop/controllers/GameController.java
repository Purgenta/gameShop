package com.purgenta.gameshop.controllers;

import com.purgenta.gameshop.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;
    @PostMapping("/addGame")
    public ResponseEntity<Map<String,String>> addGame(MultipartFile imageFile) {
       return gameService.addGame(imageFile);
    }
}
