package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.models.game.Game;
import com.purgenta.gameshop.models.game.GameImage;
import com.purgenta.gameshop.repositories.IGameImageRepository;
import com.purgenta.gameshop.requests.RemoveImageRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameImageService implements IGameImageService{
    private final IGameImageRepository gameImageRepository;



    @Override
    public void saveImage(String imagePath, Game game) {
        GameImage gameImage = GameImage.builder().isActive(true).fileName(imagePath).game(game).build();
        gameImageRepository.save(gameImage);
    }

    @Override
    public ResponseEntity<?> removeImage(RemoveImageRequest removeImageRequest) {
        return null;
    }


}
