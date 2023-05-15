package com.purgenta.gameshop.controllers;

import com.purgenta.gameshop.dto.GameFilterDto;
import com.purgenta.gameshop.requests.GameImageRequest;
import com.purgenta.gameshop.requests.GameRequest;
import com.purgenta.gameshop.requests.RemoveImageRequest;
import com.purgenta.gameshop.services.game.IGameImageService;
import com.purgenta.gameshop.services.game.IGameService;
import com.purgenta.gameshop.services.game.IProductManagementService;
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
    private final IProductManagementService productManagementService;
    private final IGameImageService gameImageService;
    @GetMapping("/getGames")
    public ResponseEntity<?> getGames(GameFilterDto gameFilterDto) {
        return gameService.getGames(gameFilterDto);
    }
    @PostMapping("/addGameImages")
    public ResponseEntity<Map<String,String>> addGameImages(@Valid GameImageRequest gameImageRequest) {
       return productManagementService.addGameImages(gameImageRequest.getImages(), gameImageRequest.getGameId());
    }

    @PostMapping("/addGame")
    public ResponseEntity<?> addGame(@RequestBody @Valid GameRequest gameRequest) {
        return productManagementService.addGame(gameRequest);
    }
    @DeleteMapping("/deleteGame/{gameId}")
    public ResponseEntity<Map<String,String>> deleteGame(@PathVariable("gameId") int gameId) {
        return productManagementService.deleteGame(gameId);
    }
    @PutMapping("/updateGame/{gameId}")
    public ResponseEntity<Map<String,String>> updateGame(@RequestBody @Valid GameRequest gameRequest, @PathVariable int gameId) {
        return productManagementService.updateGame(gameRequest,gameId);
    }
    @GetMapping("/game/{gameId}")
    public ResponseEntity<?> getGame(@PathVariable int gameId) {
        return gameService.getGame(gameId);
    }
    @DeleteMapping("/removeImage")
    public ResponseEntity<?> removeImage(@Valid RemoveImageRequest removeImageRequest) {
        return gameImageService.removeImage(removeImageRequest);
    }
    @GetMapping("/filterValues")
    public ResponseEntity<?> getFilterValues() {
        return gameService.getFilterValues();
    }
}
