package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.GameCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGameCategoryRepository extends JpaRepository<GameCategory,Integer> {
}
