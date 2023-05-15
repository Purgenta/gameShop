package com.purgenta.gameshop.services.cart;

import com.purgenta.gameshop.dto.GameDto;
import com.purgenta.gameshop.models.Game;
import com.purgenta.gameshop.repositories.IGameRepository;
import com.purgenta.gameshop.requests.CartDataRequest;
import com.purgenta.gameshop.services.game.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final IGameRepository iGameRepository;
    private final GameService gameService;
    @Override
    public ResponseEntity<?> verifyCart(CartDataRequest cartDataRequest) {
        List<Integer> cartIds = new ArrayList<>();
        cartDataRequest.getCart().forEach(cartItem -> cartIds.add(cartItem.getGameId()));
        HashSet<Integer> uniqueIds = new HashSet<>(cartIds);
        if(cartIds.size() != uniqueIds.size()) return  ResponseEntity.badRequest().build();
        List<Game> cartGames = iGameRepository.findByIdInAndIsSelling(cartIds,true);
        List<GameDto> gameDtos = new ArrayList<>();
        cartGames.forEach(game -> gameDtos.add(gameService.buildGameDto(game)));
        Map<String,Object> response = new HashMap<>();
        response.put("games",gameDtos);
        return ResponseEntity.ok(response);
    }
}
