package com.purgenta.gameshop.services.cart;

import com.purgenta.gameshop.models.cart.Cart;
import com.purgenta.gameshop.models.cart.CartItem;
import com.purgenta.gameshop.models.cart.CartStatus;
import com.purgenta.gameshop.models.game.Game;
import com.purgenta.gameshop.models.user.User;
import com.purgenta.gameshop.repositories.ICartItemRepository;
import com.purgenta.gameshop.repositories.ICartRepository;
import com.purgenta.gameshop.response.cart.CartItemCountResponse;
import com.purgenta.gameshop.services.game.IGameService;
import com.purgenta.gameshop.services.order.IOrderService;
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
    private final IGameService gameService;
    private final IOrderService iOrderService;


    @Override
    public ResponseEntity<?> addCartItem(int game_id) {
        Optional<Game> game = gameService.findGameById(game_id);
        if(game.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Cart cart = findUserCartOrCreate();
        int count = cart.getCartItems().size();
        CartItem cartItem;
        Optional<CartItem> foundItem = findCartItem(cart,game.get());
        if(foundItem.isPresent()) {
            cartItem = foundItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = CartItem.builder().cart(cart).quantity(1).game(game.get()).build();
            count++;
        }
        iCartItemRepository.save(cartItem);
        return new ResponseEntity<>(new CartItemCountResponse(count),HttpStatus.OK);
    }
    public ResponseEntity<?> deleteCartItem(int game_id) {
        Optional<Game> game = gameService.findGameById(game_id);
        if(game.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Cart cart = findUserCartOrCreate();
        Optional<CartItem> foundItem = findCartItem(cart,game.get());
        foundItem.ifPresent(iCartItemRepository::delete);
        return new ResponseEntity<>(new CartItemCountResponse(cart.getCartItems().size() -1),HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> setCartItem(int game_id, int quantity) {
        Optional<Game> game = gameService.findGameById(game_id);
        if(game.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Cart cart = findUserCartOrCreate();
        CartItem cartItem;
        Optional<CartItem> foundItem = findCartItem(cart,game.get());
        int count = cart.getCartItems().size();
        if(foundItem.isPresent()) {
            cartItem = foundItem.get();
            cartItem.setQuantity(quantity);
        } else {
            cartItem = CartItem.builder().cart(cart).quantity(quantity).game(game.get()).build();
            iCartItemRepository.save(cartItem);
            count ++;
        }
        iCartItemRepository.save(cartItem);
        return new ResponseEntity<>(new CartItemCountResponse(count),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> cartItemCount() {
        return ResponseEntity.ok(new CartItemCountResponse(  countCart()));
    }
    public int countCart() {
        Cart userCart = findUserCartOrCreate();
        return userCart.getCartItems().size();
    }

    @Override
    public ResponseEntity<?> getUserCart() {
        return new ResponseEntity<>(findUserCartOrCreate().getCartItems(),HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> checkout() {
        var cart = findUserCartOrCreate();
        iOrderService.placeOrder(cart);
        var size = cart.getCartItems().size();
        if(size < 1) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        cart.setCartStatus(CartStatus.ISSUED);
        iCartRepository.save(cart);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
