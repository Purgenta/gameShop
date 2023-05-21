package com.purgenta.gameshop.services.cart;

import org.springframework.http.ResponseEntity;

public interface ICartService {
    ResponseEntity<?> addCartItem(int game_id);
    ResponseEntity<?> setCartItem(int game_id,int quantity);
    ResponseEntity<?> deleteCartItem(int game_id);
    ResponseEntity<?> cartItemCount();
    ResponseEntity<?> getUserCart();
}
