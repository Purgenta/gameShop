package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.dto.GameDto;
import com.purgenta.gameshop.dto.GameFilterDto;
import com.purgenta.gameshop.dto.GameSpecification;
import com.purgenta.gameshop.models.Game;
import com.purgenta.gameshop.models.GameCategory;
import com.purgenta.gameshop.models.Publisher;
import com.purgenta.gameshop.models.User;
import com.purgenta.gameshop.repositories.IGameRepository;
import com.purgenta.gameshop.requests.GameRequest;
import com.purgenta.gameshop.services.file.IFileService;
import com.purgenta.gameshop.services.publisher.IPublisherService;
import com.purgenta.gameshop.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final GameSpecification gameSpecification;
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
    public ResponseEntity<Map<String, String>> addGame(GameRequest gameRequest) {
        Map<String,String> response = new HashMap<>();
        gameRepository.save(buildGame(gameRequest));
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
    public ResponseEntity<Map<String, String>> updateGame(GameRequest gameRequest, int gameId) {
        Map<String,String> response = new HashMap<>();
        Optional<Game> gameToUpdate = gameRepository.findById(gameId);
        if(gameToUpdate.isEmpty()) {
            response.put("errorMessage","No such game exists");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        Game game = buildGame(gameRequest);
        game.setId(gameId);
        gameRepository.save(game);
        response.put("success","successfully updated");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public Game buildGame(GameRequest gameRequest) {
        User user = userService.getAuthenticatedUser();
        Publisher publisher = publisherService.getPublisherById(gameRequest.getPublisherId()).get();
        GameCategory gameCategory = iGameCategoryService.getCategory(gameRequest.getCategoryId()).get();
        return Game.builder().price(gameRequest.getPrice()).category(gameCategory).publisher(publisher).isSelling(true).
                title(gameRequest.getTitle()).description(gameRequest.getDescription()).releaseYear(gameRequest.getReleaseYear()).user(user).build();
    }
    public ResponseEntity<?> getGame (int gameId) {
        Optional<Game> game = gameRepository.findById(gameId);
        if(game.isPresent()) return new ResponseEntity<>(buildGameDto(game.get()),HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Map<String,Object>> getGames(GameFilterDto gameFilterDto) {
        try {
            List<Sort.Order> orders = orderBy(gameFilterDto.getSort());
            List<Game> games;
            Pageable pagingSort = PageRequest.of(gameFilterDto.getPage(), gameFilterDto.getSize(),Sort.by(orders));
            Page<Game> pageGames;
            pageGames = gameRepository.findAll(gameSpecification.searchForGamesUnderCondition(gameFilterDto),pagingSort);
            games = pageGames.getContent();
            if(games.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<GameDto> gameDtos = new ArrayList<>();
            games.forEach(game -> gameDtos.add(buildGameDto(game))
            );
            Map<String,Object> response = new HashMap<>();
            Map<String,Object> pagination = new HashMap<>();
            response.put("games",gameDtos);
            pagination.put("totalElements", pageGames.getTotalElements());
            pagination.put("perPage",gameFilterDto.getSize());
            pagination.put("totalPages",pageGames.getTotalPages());
            response.put("pagination",pagination);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }
    private List<Sort.Order> orderBy(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();
        if(sort == null) return orders;
        if(sort[0].contains(",")) {
            for (String sortOrder: sort) {
                String [] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]),_sort[0]));
            }
        }
        else {
            orders.add(new Sort.Order(getSortDirection(sort[1]),sort[0]));
        }
        return orders;
    }
    public static GameDto buildGameDto(Game game) {
       return GameDto.builder().gameImages(game.getGameImageList()).id(game.getId()).gameCategory(game.getCategory()).price(game.getPrice())
                .title(game.getTitle()).releaseYear(game.getReleaseYear()).publisher(game.getPublisher()).gameImages(game.getGameImageList()).build();
    }

}
