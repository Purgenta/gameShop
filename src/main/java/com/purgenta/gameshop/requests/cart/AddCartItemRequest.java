package com.purgenta.gameshop.requests.cart;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class AddCartItemRequest {
    @Min(0)
    private int game_id;
}
