package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.dto.GameFilterDto;
import com.purgenta.gameshop.models.game.Game;
import com.purgenta.gameshop.requests.GameRequest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IGameService {
    ResponseEntity<?> getGame (int gameId);
    ResponseEntity<?> getFilterValues();
    ResponseEntity<?> getGames(GameFilterDto gameFilterDto);
    Optional<Game> findGameById(int game_id);

    Game buildGame(GameRequest gameRequest);
}
