package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface IGameRepository extends JpaRepository<Game, Integer> {
    Optional<Game> findByTitle(String title);
}
