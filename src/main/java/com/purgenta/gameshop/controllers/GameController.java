package com.purgenta.gameshop.controllers;

import com.purgenta.gameshop.requests.GameRequest;
import com.purgenta.gameshop.services.game.IGameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {
    private final IGameService gameService;
    @PostMapping("/addGameImage")
    public ResponseEntity<Map<String,String>> addGameImage(MultipartFile imageFile,int gameId) {
       return gameService.addGameImage(imageFile,gameId);
    }
    @PostMapping("/addGame")
    public ResponseEntity<?> addGame(@ModelAttribute @Valid GameRequest gameRequest) {
        return gameService.addGame(gameRequest);
    }
}
