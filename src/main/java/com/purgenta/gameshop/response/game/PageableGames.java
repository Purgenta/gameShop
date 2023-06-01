package com.purgenta.gameshop.response.game;

import com.purgenta.gameshop.models.game.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class PageableGames {
    private int numberOfPages;
    private List<Game> games;
}
