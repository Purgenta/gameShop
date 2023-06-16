package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.dto.GameFilterDto;
import com.purgenta.gameshop.models.game.Game;
import com.purgenta.gameshop.requests.game.GameRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IGameService {
    ResponseEntity<?> getGame (int gameId);
    ResponseEntity<?> getFilterValues();
    ResponseEntity<?> getGames(GameFilterDto gameFilterDto);
    Optional<Game> findGameById(int game_id);
    ResponseEntity<?> deleteImage(long image_id);

    ResponseEntity<?>addGame(GameRequest request) throws Exception;
    ResponseEntity<?> deleteGame(int game_id);
    ResponseEntity<?> updateGame(GameRequest gameRequest, int game_id);
    ResponseEntity<?> getPageableGames(int page);
    Long getGameCount();

}
