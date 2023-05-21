package com.purgenta.gameshop.services.cart;

import com.purgenta.gameshop.models.cart.Cart;
import com.purgenta.gameshop.models.cart.CartItem;
import com.purgenta.gameshop.models.cart.CartStatus;
import com.purgenta.gameshop.models.game.Game;
import com.purgenta.gameshop.models.user.User;
import com.purgenta.gameshop.repositories.ICartItemRepository;
import com.purgenta.gameshop.repositories.ICartRepository;
import com.purgenta.gameshop.response.cart.CartItemCountResponse;
import com.purgenta.gameshop.services.game.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final ICartRepository iCartRepository;
    private final ICartItemRepository iCartItemRepository;
    private final GameService gameService;


    @Override
    public ResponseEntity<?> addCartItem(int game_id) {
        Optional<Game> game = gameService.findGameById(game_id);
        if(game.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Cart cart = findUserCartOrCreate();
        CartItem cartItem;
        Optional<CartItem> foundItem = findCartItem(cart,game.get());
        if(foundItem.isPresent()) {
            cartItem = foundItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = CartItem.builder().cart(cart).quantity(1).game(game.get()).build();
            iCartItemRepository.save(cartItem);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    public ResponseEntity<?> deleteCartItem(int game_id) {
        Optional<Game> game = gameService.findGameById(game_id);
        if(game.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Cart cart = findUserCartOrCreate();
        Optional<CartItem> foundItem = findCartItem(cart,game.get());
        foundItem.ifPresent(iCartItemRepository::delete);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> setCartItem(int game_id, int quantity) {
        Optional<Game> game = gameService.findGameById(game_id);
        if(game.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Cart cart = findUserCartOrCreate();
        CartItem cartItem;
        Optional<CartItem> foundItem = findCartItem(cart,game.get());
        if(foundItem.isPresent()) {
            cartItem = foundItem.get();
            cartItem.setQuantity(quantity);
        } else {
            cartItem = CartItem.builder().cart(cart).quantity(quantity).game(game.get()).build();
            iCartItemRepository.save(cartItem);
        }
        iCartItemRepository.save(cartItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> cartItemCount() {
        Cart userCart = findUserCartOrCreate();
        int count = iCartItemRepository.countCartItemByCart(userCart);
        return ResponseEntity.ok(new CartItemCountResponse(count));
    }

    @Override
    public ResponseEntity<?> getUserCart() {
        return new ResponseEntity<>(findUserCartOrCreate(),HttpStatus.OK);
    }

    private Cart findUserCartOrCreate() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Cart> cart = iCartRepository.findCartByUserAndCartStatus(user, CartStatus.ONGOING);
        return cart.orElseGet(() -> iCartRepository.save(Cart.builder().cartStatus(CartStatus.ONGOING).user(user).cartItems(new ArrayList<>()).build()));
    }
    private Optional<CartItem> findCartItem(Cart cart,Game game) {
        return iCartItemRepository.findCartItemByCartAndGame(cart,game);
    }
}
