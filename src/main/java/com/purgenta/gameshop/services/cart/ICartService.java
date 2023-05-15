package com.purgenta.gameshop.services.cart;

import com.purgenta.gameshop.requests.CartDataRequest;
import org.springframework.http.ResponseEntity;

public interface ICartService {
    ResponseEntity<?> verifyCart(CartDataRequest cartDataRequest);
}
