package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.cart.Cart;
import com.purgenta.gameshop.models.cart.CartItem;
import com.purgenta.gameshop.models.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICartItemRepository extends JpaRepository<CartItem,Long> {
    Integer countCartItemByCart(Cart cart);
    Optional<CartItem> findCartItemByCartAndGame(Cart cart,Game game);
}
