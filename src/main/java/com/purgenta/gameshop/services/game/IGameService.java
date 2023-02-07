package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.dto.GameFilterDto;
import com.purgenta.gameshop.models.Game;
import com.purgenta.gameshop.requests.GameRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IGameService {
    ResponseEntity<Map<String, String>> addGameImages(MultipartFile[] images,int gameId);

    ResponseEntity<Map<String,String>> addGame(GameRequest gameRequest);
    ResponseEntity<Map<String,String>> deleteGame(int gameId);
    ResponseEntity<?> getGame (int gameId);

    ResponseEntity<?> getGames(GameFilterDto gameFilterDto);
    ResponseEntity<Map<String,String>> updateGame(GameRequest gameRequest, int gameId);

    Game buildGame(GameRequest gameRequest);
}
