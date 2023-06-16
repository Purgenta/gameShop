package com.purgenta.gameshop.requests.review;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReviewRequest {
    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private int rating;
    @NotNull
    private int game_id;
    @NotNull
    @Size(min = 15,max = 200)
    private String review;
}
