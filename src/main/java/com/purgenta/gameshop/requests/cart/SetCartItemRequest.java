package com.purgenta.gameshop.requests.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SetCartItemRequest {
    @NotNull
    @Min(0)
    private Integer gameId;
    @Min(1)
    private Integer quantity;
}
