package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.models.Game;
import com.purgenta.gameshop.models.GameCategory;
import com.purgenta.gameshop.models.Publisher;
import com.purgenta.gameshop.models.User;
import com.purgenta.gameshop.repositories.IGameRepository;
import com.purgenta.gameshop.requests.GameRequest;
import com.purgenta.gameshop.services.IUserService;
import com.purgenta.gameshop.services.file.IFileService;
import com.purgenta.gameshop.services.publisher.IPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService implements IGameService {
    private final IFileService fileService;
    private final IPublisherService publisherService;
    private final IUserService userService;
    private final IGameCategoryService iGameCategoryService;
    private final IGameRepository gameRepository;
    @Override
    public ResponseEntity<Map<String, String>> addGameImage(MultipartFile file,int gameId) {
        Map<String,String> response = new HashMap<>();
        try {
            Optional<Game> game = gameRepository.findById(gameId);
            if(game.isEmpty()) {
                response.put("errorMessage","No such game exists");
                return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
            }
            else {
                String imagePath = fileService.saveProductImage(file);
                Game updatedGame = game.get();
                updatedGame.setImg_path(imagePath);
                gameRepository.save(updatedGame);
            }
        } catch (IOException e) {
            response.clear();
            response.put("error","Issues with processing your image file");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String, String>> addGame(GameRequest gameRequest) {
        User user = userService.getAuthenticatedUser();
        Map<String,String> response = new HashMap<>();
        Publisher publisher = publisherService.getPublisherById(gameRequest.getPublisherId()).get();
        GameCategory gameCategory = iGameCategoryService.getCategory(gameRequest.getCategoryId()).get();
        String imagePath;
        try {
            imagePath = fileService.saveProductImage(gameRequest.getImage());
        } catch (IOException e) {
            response.put("errorMessage","Error processing your image");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        var game = Game.builder().price(gameRequest.getPrice()).category(gameCategory).publisher(publisher).img_path(imagePath).
                title(gameRequest.getTitle()).description(gameRequest.getDescription()).releaseYear(gameRequest.getReleaseYear()).user(user).build();
        System.out.println(game);
        gameRepository.save(game);
        response.put("success","Product was created");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
