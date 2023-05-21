package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.models.game.Game;
import com.purgenta.gameshop.repositories.IGameRepository;
import com.purgenta.gameshop.requests.GameRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductManagementService implements IProductManagementService{
    private final IGameRepository gameRepository;
    private final IGameService gameService;
    private final IGameImageService imageService;

    public ResponseEntity<Map<String, String>> addGame(GameRequest gameRequest) {
        Map<String,String> response = new HashMap<>();
        gameRepository.save(gameService.buildGame(gameRequest));
        response.put("success","Product was created");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String, String>> addGameImages(MultipartFile[] images, int gameId) {
        return null;
    }

    public ResponseEntity<Map<String,String>> deleteGame(int gameId) {
        Optional<Game> game = gameRepository.findById(gameId);
        if(game.isPresent() && game.get().isSelling()) {
            Game deletedGame = game.get();
            deletedGame.setSelling(false);
            gameRepository.save(deletedGame);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> updateGame(GameRequest gameRequest, int gameId) {
        Map<String,String> response = new HashMap<>();
        Optional<Game> gameToUpdate = gameRepository.findById(gameId);
        if(gameToUpdate.isEmpty()) {
            response.put("errorMessage","No such game exists");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        Game game = gameService.buildGame(gameRequest);
        game.setId(gameId);
        gameRepository.save(game);
        response.put("success","successfully updated");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
