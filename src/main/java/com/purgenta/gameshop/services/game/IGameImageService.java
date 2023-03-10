package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.models.Game;
import com.purgenta.gameshop.dto.RemoveImageDto;
import org.springframework.http.ResponseEntity;

public interface IGameImageService {
    void saveImage(String imagePath, Game game);
    ResponseEntity<?> removeImage(RemoveImageDto removeImageDto);
}
