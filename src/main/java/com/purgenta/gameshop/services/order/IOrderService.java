package com.purgenta.gameshop.services.order;

import com.purgenta.gameshop.models.cart.Cart;
import com.purgenta.gameshop.models.game.Game;
import com.purgenta.gameshop.models.order.Order;
import com.purgenta.gameshop.models.user.User;
import com.purgenta.gameshop.requests.order.OrderReviewRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrderService {
    boolean findPlacedOrder(User user, Game game);
    Order placeOrder(Cart cart);
    List<Order> getUserOrders(User user);
    ResponseEntity<?> deleteReview(int reviewId);
    ResponseEntity<?> addReview(OrderReviewRequest orderReview, int orderId);
    Long getOrderCount ();
}
