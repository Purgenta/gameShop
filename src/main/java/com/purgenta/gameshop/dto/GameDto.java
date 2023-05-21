package com.purgenta.gameshop.dto;

import com.purgenta.gameshop.models.game.GameCategory;
import com.purgenta.gameshop.models.game.GameImage;
import com.purgenta.gameshop.models.game.Publisher;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GameDto {
    private int id;
    private double price;

    private String title;
    private Publisher publisher;
    private GameCategory gameCategory;
    private List<GameImage> gameImages;

    private int releaseYear;

}
