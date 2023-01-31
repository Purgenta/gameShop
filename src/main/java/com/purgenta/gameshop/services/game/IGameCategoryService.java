package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.models.GameCategory;

import java.util.List;
import java.util.Optional;

public interface IGameCategoryService {

    List<GameCategory> getCategories();

    void addGameCategory(GameCategory category);
    Optional<GameCategory> getCategory (int gameCategoryId);
}
