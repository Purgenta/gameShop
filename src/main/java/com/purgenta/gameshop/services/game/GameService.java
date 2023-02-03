package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.models.Game;
import com.purgenta.gameshop.models.GameCategory;
import com.purgenta.gameshop.models.Publisher;
import com.purgenta.gameshop.models.User;
import com.purgenta.gameshop.repositories.IGameRepository;
import com.purgenta.gameshop.dto.GameDto;
import com.purgenta.gameshop.services.user.IUserService;
import com.purgenta.gameshop.services.file.IFileService;
import com.purgenta.gameshop.services.publisher.IPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService implements IGameService {
    private final IFileService fileService;
    private final IPublisherService publisherService;
    private final IUserService userService;
    private final IGameCategoryService iGameCategoryService;
    private final IGameImageService imageService;
    private final IGameRepository gameRepository;
    @Override
    public ResponseEntity<Map<String, String>> addGameImages(MultipartFile[] images,int gameId) {
        Map<String,String> response = new HashMap<>();

            Optional<Game> game = gameRepository.findById(gameId);
            if(game.isEmpty()) {
                response.put("errorMessage","No such game exists");
                return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
            }
            else {
                Game affected = game.get();
                try {
                    List<String> imagePaths = new ArrayList<>();
                    Arrays.stream(images).toList().forEach(image -> {
                        try {
                            String imagePath = fileService.saveProductImage(image);
                            imagePaths.add(imagePath);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    imagePaths.forEach(productImage -> imageService.saveImage(productImage,affected));
                }
                catch (Exception e) {
                    response.put("error","Issues with processing your image file");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
            }
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String, String>> addGame(GameDto gameDto) {
        Map<String,String> response = new HashMap<>();
        gameRepository.save(buildGame(gameDto));
        response.put("success","Product was created");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    public ResponseEntity<Map<String,String>> deleteGame(int gameId) {
        Optional<Game> game = gameRepository.findById(gameId);
        if(game.isPresent() && game.get().isSelling()) {
            Game deletedGame = game.get();
            deletedGame.setSelling(false);
            gameRepository.save(deletedGame);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> updateGame(GameDto gameDto, int gameId) {
        Map<String,String> response = new HashMap<>();
        Optional<Game> gameToUpdate = gameRepository.findById(gameId);
        if(gameToUpdate.isEmpty()) {
            response.put("errorMessage","No such game exists");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        Game game = buildGame(gameDto);
        game.setId(gameId);
        gameRepository.save(game);
        response.put("success","successfully updated");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public Game buildGame(GameDto gameDto) {
        User user = userService.getAuthenticatedUser();
        Publisher publisher = publisherService.getPublisherById(gameDto.getPublisherId()).get();
        GameCategory gameCategory = iGameCategoryService.getCategory(gameDto.getCategoryId()).get();
        return Game.builder().price(gameDto.getPrice()).category(gameCategory).publisher(publisher).isSelling(true).
                title(gameDto.getTitle()).description(gameDto.getDescription()).releaseYear(gameDto.getReleaseYear()).user(user).build();
    }
    public ResponseEntity<?> getGame (int gameId) {
        Optional<Game> game = gameRepository.findById(gameId);
        if(game.isPresent()) return new ResponseEntity<>(game.get(),HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
