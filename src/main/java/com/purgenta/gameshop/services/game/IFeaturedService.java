package com.purgenta.gameshop.services.game;

import org.springframework.http.ResponseEntity;

public interface IFeaturedService {
    ResponseEntity<?> featuredGames();
}
