package com.purgenta.gameshop.controllers;

import com.purgenta.gameshop.dto.GameImageDto;
import com.purgenta.gameshop.dto.GameDto;
import com.purgenta.gameshop.dto.RemoveImageDto;
import com.purgenta.gameshop.services.game.IGameImageService;
import com.purgenta.gameshop.services.game.IGameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {
    private final IGameService gameService;

    private final IGameImageService gameImageService;
    @PostMapping("/addGameImage")
    public ResponseEntity<Map<String,String>> addGameImages(@Valid GameImageDto gameImageDto) {
       return gameService.addGameImages(gameImageDto.getImages(), gameImageDto.getGameId());
    }

    @PostMapping("/addGame")
    public ResponseEntity<?> addGame(@RequestBody @Valid GameDto gameDto) {
        return gameService.addGame(gameDto);
    }
    @DeleteMapping("/deleteGame/{gameId}")
    public ResponseEntity<Map<String,String>> deleteGame(@PathVariable("gameId") int gameId) {
        return gameService.deleteGame(gameId);
    }
    @PutMapping("/updateGame/{gameId}")
    public ResponseEntity<Map<String,String>> updateGame(@RequestBody @Valid GameDto gameDto, @PathVariable int gameId) {
        return gameService.updateGame(gameDto,gameId);
    }
    @GetMapping("/game/{gameId}")
    public ResponseEntity<?> getGame(@PathVariable int gameId) {
        return gameService.getGame(gameId);
    }
    @DeleteMapping("/removeImage")
    public ResponseEntity<?> removeImage(@Valid RemoveImageDto removeImageDto) {
        return gameImageService.removeImage(removeImageDto);
    }
}
