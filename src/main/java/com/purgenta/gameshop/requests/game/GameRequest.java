package com.purgenta.gameshop.requests.game;

import com.purgenta.gameshop.validation.gamecategory.ValidateGameCategoryId;
import com.purgenta.gameshop.validation.publisher.ValidatePublisherId;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GameRequest {
    @NotNull
    private String title;
    @NotNull
    @Column(columnDefinition = "MEDIUMTEXT")
    @Size(min = 15,max = 1000)
    private String description;

    @NotNull
    @ValidatePublisherId
    private int publisher_id;
    @NotNull
    @ValidateGameCategoryId
    private int category_id;
    @Min(1950)
    private int release_year;
    @NotNull
    @Min(1)
    private double price;
    @NotNull
    private String[] images;
}
