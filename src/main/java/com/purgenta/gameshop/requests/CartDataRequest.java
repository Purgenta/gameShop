package com.purgenta.gameshop.requests;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CartDataRequest {
    @Valid
    @NotEmpty
    List<CartItem> cart;
}
