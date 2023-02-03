package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.models.Game;
import com.purgenta.gameshop.dto.GameDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IGameService {
    ResponseEntity<Map<String, String>> addGameImages(MultipartFile[] images,int gameId);

    ResponseEntity<Map<String,String>> addGame(GameDto gameDto);
    ResponseEntity<Map<String,String>> deleteGame(int gameId);
    ResponseEntity<?> getGame (int gameId);
    ResponseEntity<Map<String,String>> updateGame(GameDto gameDto, int gameId);

    Game buildGame(GameDto gameDto);
}
