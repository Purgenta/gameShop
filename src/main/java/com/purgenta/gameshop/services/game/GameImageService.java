package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.models.Game;
import com.purgenta.gameshop.models.GameImage;
import com.purgenta.gameshop.repositories.IGameImageRepository;
import com.purgenta.gameshop.requests.RemoveImageRequest;
import com.purgenta.gameshop.services.file.IFileService;
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

    private final IFileService fileService;

    @Override
    public void saveImage(String imagePath, Game game) {
        GameImage gameImage = GameImage.builder().isActive(true).fileName(imagePath).game(game).build();
        gameImageRepository.save(gameImage);
    }


    public ResponseEntity<?> removeImage(@Valid RemoveImageRequest removeImageRequest) {
        Optional<GameImage> gameImage = gameImageRepository.findById(removeImageRequest.getImageId());
        if(gameImage.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        GameImage image = gameImage.get();
        gameImageRepository.delete(image);
        try {
            fileService.removeProductImage(image.getFileName());
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
