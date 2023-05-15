package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.dto.GameFilterDto;
import com.purgenta.gameshop.models.Game;
import com.purgenta.gameshop.requests.GameRequest;
import org.springframework.http.ResponseEntity;

public interface IGameService {
    ResponseEntity<?> getGame (int gameId);
    ResponseEntity<?> getFilterValues();
    ResponseEntity<?> getGames(GameFilterDto gameFilterDto);


    Game buildGame(GameRequest gameRequest);
}
