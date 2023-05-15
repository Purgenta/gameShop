package com.purgenta.gameshop.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItem {
    @NotNull
    private Integer gameId;
    @Min(1)
    private Integer quantity;
}
