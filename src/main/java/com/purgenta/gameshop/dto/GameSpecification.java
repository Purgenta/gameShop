package com.purgenta.gameshop.dto;

import com.purgenta.gameshop.models.game.Game;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class GameSpecification {

    public Specification<Game> gameLike (String search) {
        return (root,query,cb) ->
            cb.like(root.get("title"),"%" + search + "%");
    }
    public Specification<Game> betweenPrice (Double fromPrice, Double toPrice) {
        return (root,query,cb) ->
                cb.between(root.get("price"),fromPrice,toPrice);
    }
    public Specification<Game> isSelling () {
        return (root,query,cb) ->
                cb.isTrue(root.get("isSelling"));
    }
    public Specification<Game> searchForGamesUnderCondition(GameFilterDto gameFilterDto) {
        Specification<Game> gameSpec;
        gameSpec = where(isSelling());
        if(gameFilterDto.getSearch() != null) {
            gameSpec = gameSpec.and(gameLike(gameFilterDto.getSearch()));
        }
        if(gameFilterDto.getSearchCategories() != null) {
            gameSpec = gameSpec.and(searchCategory(gameFilterDto.getSearchCategories()));
        }
        if(gameFilterDto.getFromPrice() != null && gameFilterDto.getToPrice() != null) {
            gameSpec = gameSpec.and(betweenPrice(gameFilterDto.getFromPrice(), gameFilterDto.getToPrice()));
        }
        if(gameFilterDto.getFromYear() != null && gameFilterDto.getToYear() != null) {
            gameSpec = gameSpec.and(releaseYearBetween(gameFilterDto.getFromYear(), gameFilterDto.getToYear()));
        }
        return gameSpec;
    }
    public Specification<Game> releaseYearBetween(int fromYear,int toYear) {
        return (root,query,cb) ->
                cb.between(root.get("releaseYear"),fromYear,toYear);
    }
    public Specification<Game> searchCategory(List<Integer> categories) {
        return (root,query,cb) ->
                root.get("category").get("id").in(categories);
    }
}
