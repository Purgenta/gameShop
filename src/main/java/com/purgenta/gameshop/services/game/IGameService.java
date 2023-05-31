package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.dto.GameFilterDto;
import com.purgenta.gameshop.models.game.Game;
import com.purgenta.gameshop.requests.game.GameRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface IGameService {
    ResponseEntity<?> getGame (int gameId);
    ResponseEntity<?> getFilterValues();
    ResponseEntity<?> getGames(GameFilterDto gameFilterDto);
    Optional<Game> findGameById(int game_id);

    ResponseEntity<?>addGame(GameRequest request);
    ResponseEntity<?> deleteGame(int game_id);
    ResponseEntity<?> updateGame(GameRequest gameRequest, int game_id);

    Game buildGame(GameRequest gameRequest);
}
