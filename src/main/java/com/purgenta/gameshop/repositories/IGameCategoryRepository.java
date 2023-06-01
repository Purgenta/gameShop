package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.game.GameCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IGameCategoryRepository extends JpaRepository<GameCategory,Integer> {
    Optional <GameCategory> findById(int id);
}
