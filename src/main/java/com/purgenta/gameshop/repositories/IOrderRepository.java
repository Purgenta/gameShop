package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.cart.Cart;
import com.purgenta.gameshop.models.order.Order;
import com.purgenta.gameshop.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<Order,Integer> {
    Optional<Order> findOrderByCart(Cart cart);
    List<Order> findAllByCartIn(List<Cart> carts);
    @Query(nativeQuery = true,value = "SELECT orders.* FROM orders " +
            "INNER JOIN cart " +
            "ON cart.id = orders.cart_id " +
            "INNER JOIN cart_item " +
            "ON cart_item.cart_id = orders.cart_id "+
            "WHERE orders.order_status = 'COMPLETED' AND cart.user_user_id = ?1 AND cart_item.game_id = ?2")
    List<Object> findPlacedOrder(long user_id, int game_id);
    List<Order> findOrderByUser(User user);
}
