package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.game.GameImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IGameImageRepository extends JpaRepository<GameImage,Long> {
    List<GameImage> findByGame_Id (int gameId);

}
