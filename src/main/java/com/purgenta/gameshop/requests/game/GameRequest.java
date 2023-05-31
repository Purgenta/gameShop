package com.purgenta.gameshop.requests.game;

import com.purgenta.gameshop.validation.game.ValidateUniqueTitle;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GameRequest {
    @NotNull
    @ValidateUniqueTitle
    private String title;
    @NotNull
    @Size(min = 15,max = 40)
    private String description;

    @NotNull
    private int publisherId;

    @NotNull
    private int categoryId;
    @Min(1950)
    private int releaseYear;
    @NotNull
    @Min(1)
    private double price;
    private String image_url;
}