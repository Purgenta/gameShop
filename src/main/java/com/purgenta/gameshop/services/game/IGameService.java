package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.requests.GameRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IGameService {
    ResponseEntity<Map<String, String>> addGameImage(MultipartFile file,int gameId);

    ResponseEntity<Map<String,String>> addGame(GameRequest gameRequest);
}
