package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.game.Game;
import com.purgenta.gameshop.models.game.GameImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGameImageRepository extends JpaRepository<GameImage,Long> {

    void deleteAllByGame(Game game);
}
