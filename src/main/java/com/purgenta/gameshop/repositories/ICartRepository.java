package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.cart.Cart;
import com.purgenta.gameshop.models.cart.CartStatus;
import com.purgenta.gameshop.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ICartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findCartByUserAndCartStatus(User user, CartStatus status);
}
