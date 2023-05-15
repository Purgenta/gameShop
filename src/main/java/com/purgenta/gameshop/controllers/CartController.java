package com.purgenta.gameshop.controllers;
import com.purgenta.gameshop.requests.CartDataRequest;
import com.purgenta.gameshop.services.cart.ICartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final ICartService cartService;
    @PostMapping("/items")
    public ResponseEntity<?> getCartData(@RequestBody @Valid CartDataRequest cartDataRequest) {
        return cartService.verifyCart(cartDataRequest);
    }
}
