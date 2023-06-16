package com.purgenta.gameshop.controllers;

import com.purgenta.gameshop.dto.GameFilterDto;
import com.purgenta.gameshop.requests.game.GameRequest;
import com.purgenta.gameshop.services.game.IFeaturedService;
import com.purgenta.gameshop.services.game.IGameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/games")
public class GameController {
    private final IGameService gameService;
    private final IFeaturedService iFeaturedService;

    @PostMapping("/getGames")
    public ResponseEntity<?> getGames(@RequestBody GameFilterDto gameFilterDto) {
        return gameService.getGames(gameFilterDto);
    }

    @PostMapping("/addGame")
    public ResponseEntity<?> addGame(@RequestBody @Valid GameRequest gameRequest) {
        try {
            return gameService.addGame(gameRequest);
        }catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/deleteGame/{gameId}")
    public ResponseEntity<?> deleteGame(@PathVariable("gameId") int gameId) {
        return gameService.deleteGame(gameId);
    }
    @PutMapping("/updateGame/{gameId}")
    public ResponseEntity<?> updateGame(@RequestBody @Valid GameRequest gameRequest, @PathVariable int gameId) {
        return gameService.updateGame(gameRequest,gameId);
    }
    @DeleteMapping("/deleteImage/{image_id}")
    public ResponseEntity<?> deleteImage(@PathVariable long image_id) {
        return gameService.deleteImage(image_id);
    }
    @GetMapping("/game/{gameId}")
    public ResponseEntity<?> getGame(@PathVariable int gameId) {
        return gameService.getGame(gameId);
    }
    @GetMapping("/paged/{page}")
    public ResponseEntity<?> getPageable(@PathVariable int page) {
        if(page <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return gameService.getPageableGames(page - 1);
    }
    @GetMapping("featuredGames")
    public ResponseEntity<?> featuredGames() {
        return iFeaturedService.featuredGames();
    }
    @GetMapping("/filterValues")
    public ResponseEntity<?> getFilterValues() {
        return gameService.getFilterValues();
    }
}
