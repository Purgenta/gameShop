package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.dto.GameDto;
import com.purgenta.gameshop.dto.GameFilterDto;
import com.purgenta.gameshop.dto.GameSpecification;
import com.purgenta.gameshop.models.game.Game;
import com.purgenta.gameshop.models.game.GameCategory;
import com.purgenta.gameshop.models.game.Publisher;
import com.purgenta.gameshop.models.user.User;
import com.purgenta.gameshop.repositories.IGameRepository;
import com.purgenta.gameshop.requests.game.GameRequest;
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

import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService implements IGameService {
    private final IPublisherService publisherService;
    private final GameSpecification gameSpecification;
    private final IUserService userService;
    private final IGameCategoryService iGameCategoryService;
    private final IGameRepository gameRepository;

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
            Pageable pagingSort = PageRequest.of(gameFilterDto.getPage() - 1, gameFilterDto.getSize(),Sort.by(orders));
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
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<Game> findGameById(int game_id) {
        return gameRepository.findById(game_id);
    }

    @Override
    public ResponseEntity<?> addGame(GameRequest request) {
        return null;
    }
    public ResponseEntity<?> deleteGame(int game_id) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateGame(GameRequest gameRequest, int game_id) {
        return null;
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
    public ResponseEntity<?> getFilterValues () {
        Map<String,Double> minMax = gameRepository.findMinMax();
        List<GameCategory> gameCategories = iGameCategoryService.getCategories();
        Map<String,Object> response = new HashMap<>();
        response.put("filter",minMax);
        response.put("categories",gameCategories);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    private GameDto buildGameDto(Game game) {
       return GameDto.builder().gameImages(game.getGameImages()).id(game.getId()).gameCategory(game.getCategory()).price(game.getPrice())
                .title(game.getTitle()).releaseYear(game.getReleaseYear()).publisher(game.getPublisher()).gameImages(game.getGameImages()).build();
    }
}
