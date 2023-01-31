package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.models.GameCategory;
import com.purgenta.gameshop.repositories.IGameCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameCategoryService implements IGameCategoryService {
    private final IGameCategoryRepository iGameCategoryRepository;

    public List<GameCategory> getCategories () {
        return iGameCategoryRepository.findAll();
    }

    public void addGameCategory(GameCategory category) {
        iGameCategoryRepository.save(category);
    }
    public Optional<GameCategory> getCategory (int gameCategoryId) {
       return iGameCategoryRepository.findById(gameCategoryId);
    }
}
