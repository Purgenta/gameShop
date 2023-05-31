package com.purgenta.gameshop.controllers;

import com.purgenta.gameshop.dto.GameFilterDto;
import com.purgenta.gameshop.requests.game.GameRequest;
import com.purgenta.gameshop.services.game.IGameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {
    private final IGameService gameService;
    @GetMapping("/getGames")
    public ResponseEntity<?> getGames(GameFilterDto gameFilterDto) {
        return gameService.getGames(gameFilterDto);
    }

    @PostMapping("/addGame")
    public ResponseEntity<?> addGame(@RequestBody @Valid GameRequest gameRequest) {
        return gameService.addGame(gameRequest);
    }
    @DeleteMapping("/deleteGame/{gameId}")
    public ResponseEntity<?> deleteGame(@PathVariable("gameId") int gameId) {
        return gameService.deleteGame(gameId);
    }
    @PutMapping("/updateGame/{gameId}")
    public ResponseEntity<?> updateGame(@RequestBody @Valid GameRequest gameRequest, @PathVariable int gameId) {
        return gameService.updateGame(gameRequest,gameId);
    }
    @GetMapping("/game/{gameId}")
    public ResponseEntity<?> getGame(@PathVariable int gameId) {
        return gameService.getGame(gameId);
    }
    @GetMapping("/filterValues")
    public ResponseEntity<?> getFilterValues() {
        return gameService.getFilterValues();
    }
}
