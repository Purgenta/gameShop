package com.purgenta.gameshop.requests;

import com.purgenta.gameshop.models.Game;
import jakarta.validation.Valid;
import lombok.Data;

@Data
public class GameRequest{
    @Valid
    public Game game;
}
