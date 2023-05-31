package com.purgenta.gameshop.controllers;

import com.purgenta.gameshop.requests.cart.AddCartItemRequest;
import com.purgenta.gameshop.requests.cart.SetCartItemRequest;
import com.purgenta.gameshop.services.cart.ICartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final ICartService cartService;
    @GetMapping("cartItems")
    public ResponseEntity<?> getCartData() {
        return cartService.getUserCart();
    }
    @GetMapping("itemCount")
    public ResponseEntity<?> getUserCartCount() {
        return cartService.cartItemCount();
    }
    @DeleteMapping("/deleteCartItem/{gameId}")
    public ResponseEntity<?> deleteCartItem( @PathVariable int gameId) {
        return cartService.deleteCartItem(gameId);
    }
    @PostMapping("addCartItem")
    public ResponseEntity<?> addCartItem(@Valid @RequestBody AddCartItemRequest addCartItemRequest) {
        return cartService.addCartItem(addCartItemRequest.getGame_id());
    }
    @PostMapping("setCartItem")
    public ResponseEntity<?> setCartItem(@Valid @RequestBody SetCartItemRequest setCartItemRequest) {
        return cartService.setCartItem(setCartItemRequest.getGame_id(), setCartItemRequest.getQuantity());
    }
}
