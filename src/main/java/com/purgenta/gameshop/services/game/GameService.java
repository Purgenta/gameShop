package com.purgenta.gameshop.services.game;

import com.purgenta.gameshop.dto.GameDto;
import com.purgenta.gameshop.dto.GameFilterDto;
import com.purgenta.gameshop.dto.GameSpecification;
import com.purgenta.gameshop.models.game.Game;
import com.purgenta.gameshop.models.game.GameCategory;
import com.purgenta.gameshop.models.game.GameImage;
import com.purgenta.gameshop.models.user.User;
import com.purgenta.gameshop.repositories.IGameImageRepository;
import com.purgenta.gameshop.repositories.IGameRepository;
import com.purgenta.gameshop.requests.game.GameRequest;
import com.purgenta.gameshop.response.game.PageableGames;
import com.purgenta.gameshop.services.publisher.IPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService implements IGameService {
    private final IPublisherService publisherService;
    private final GameSpecification gameSpecification;
    private final IGameCategoryService iGameCategoryService;
    private final IGameRepository gameRepository;
    private final IGameImageRepository iGameImageRepository;
    private final int ADMIN_DASHBOARD_PAGEABLE = 10;
    public ResponseEntity<?> deleteImage(long image_id) {
        var image = iGameImageRepository.findById(image_id);
        if(image.isPresent()) {
            iGameImageRepository.delete(image.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<?> getGame(int gameId) {
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isPresent()) return new ResponseEntity<>(buildGameDto(game.get()), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Game buildGameFromRequest(GameRequest request) throws Exception {
        var publisher = publisherService.getPublisherById(request.getPublisher_id());
        if (publisher.isEmpty()) throw new Exception("Publisher not found");
        var category = iGameCategoryService.getCategory(request.getCategory_id());
        if (category.isEmpty()) throw new Exception("Category not found");
        return Game.builder().price(request.getPrice())
                .publisher(publisher.get())
                .description(request.getDescription())
                .title(request.getTitle())
                .category(category.get())
                .releaseYear(request.getRelease_year()).selling(true).
                user((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).build();
    }

    @Override
    public ResponseEntity<Map<String, Object>> getGames(GameFilterDto gameFilterDto) {
        try {
            List<Sort.Order> orders = orderBy(gameFilterDto.getSort());
            List<Game> games;
            Pageable pagingSort = PageRequest.of(gameFilterDto.getPage() - 1, gameFilterDto.getSize(), Sort.by(orders));
            Page<Game> pageGames;
            pageGames = gameRepository.findAll(gameSpecification.searchForGamesUnderCondition(gameFilterDto), pagingSort);
            games = pageGames.getContent();
            if (games.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<GameDto> gameDtos = new ArrayList<>();
            games.forEach(game -> gameDtos.add(buildGameDto(game))
            );
            Map<String, Object> response = new HashMap<>();
            Map<String, Object> pagination = new HashMap<>();
            response.put("games", gameDtos);
            pagination.put("totalElements", pageGames.getTotalElements());
            pagination.put("perPage", gameFilterDto.getSize());
            pagination.put("totalPages", pageGames.getTotalPages());
            response.put("pagination", pagination);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Optional<Game> findGameById(int game_id) {
        return gameRepository.findById(game_id);
    }


    @Override
    public ResponseEntity<?> addGame(GameRequest request) {
        try {
            var game = gameRepository.save(buildGameFromRequest(request));
            var images = Arrays.asList(request.getImages());
            var gameImages = images.stream().map(image -> GameImage.builder().game
                    (game).url(image).build()).collect(Collectors.toList());
            iGameImageRepository.saveAll(gameImages);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> deleteGame(int game_id) {
        var game = findGameById(game_id);
        if (game.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var foundGame = game.get();
        foundGame.setSelling(false);
        gameRepository.save(foundGame);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateGame(GameRequest gameRequest, int game_id) {
        var game = findGameById(game_id);
        if (game.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try {
            var updatedGame = buildGameFromRequest(gameRequest);
            updatedGame.setId(game_id);
            gameRepository.save(updatedGame);
            var gameImages = Arrays.stream(gameRequest.getImages()).map(image -> GameImage.builder().game(updatedGame).
                    url(image).build()).collect(Collectors.toList());
            iGameImageRepository.saveAll(gameImages);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
        if (sort == null) return orders;
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        return orders;
    }

    public ResponseEntity<?> getFilterValues() {
        Map<String, Double> minMax = gameRepository.findMinMax();
        List<GameCategory> gameCategories = iGameCategoryService.getCategories();
        Map<String, Object> response = new HashMap<>();
        response.put("filter", minMax);
        response.put("categories", gameCategories);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private GameDto buildGameDto(Game game) {
        return GameDto.builder().gameImages(game.getGameImages()).id(game.getId()).gameCategory(game.getCategory()).price(game.getPrice())
                .title(game.getTitle()).releaseYear(game.getReleaseYear()).publisher(game.getPublisher()).gameImages(game.getGameImages()).build();
    }

    public ResponseEntity<?> getPageableGames(int page) {
        Pageable pageable = PageRequest.of(page, ADMIN_DASHBOARD_PAGEABLE);
        var games = gameRepository.findAllBySellingIsTrue(pageable);
        if (games.size() == 0) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        long totalPages = gameRepository.count() / ADMIN_DASHBOARD_PAGEABLE;
        return new ResponseEntity<>(new PageableGames(Math.toIntExact(totalPages),games), HttpStatus.OK);
    }
}
