package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IGameRepository extends JpaRepository<Game, Integer> {
    Game findByTitle(String title);
}
