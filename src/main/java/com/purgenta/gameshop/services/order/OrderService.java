package com.purgenta.gameshop.services.order;

import com.purgenta.gameshop.models.cart.Cart;
import com.purgenta.gameshop.models.game.Game;
import com.purgenta.gameshop.models.order.Order;
import com.purgenta.gameshop.models.order.OrderReview;
import com.purgenta.gameshop.models.order.OrderStatus;
import com.purgenta.gameshop.models.user.User;
import com.purgenta.gameshop.repositories.IOrderRepository;
import com.purgenta.gameshop.repositories.IOrderReviewRepository;
import com.purgenta.gameshop.requests.order.OrderReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final IOrderRepository iOrderRepository;
    private final IOrderReviewRepository iOrderReviewRepository;
    @Override
    public boolean findPlacedOrder(User user, Game game) {
        var orders = iOrderRepository.findPlacedOrder(user.getUser_id(), game.getId());
        return orders.isEmpty();
    }

    @Override
    public Order placeOrder(Cart cart) {
        var user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var order = Order.builder().
                orderStatus(OrderStatus.ISSUED_FOR_DELIVERY)
                .issued_at(new Date(System.currentTimeMillis()))
                .cart(cart)
                .user(user)
                .build();
        iOrderRepository.save(order);
        return order;
    }
    public List<Order> getUserOrders(User user) {
        return iOrderRepository.findOrderByUser(user);
    }

    @Override
    public ResponseEntity<?> deleteReview(int reviewId) {
        var review = iOrderReviewRepository.findById(reviewId);
        if(review.isPresent()) iOrderReviewRepository.delete(review.get());
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addReview(OrderReviewRequest orderReview, int orderId) {
        var order =iOrderRepository.findById(orderId);
        if(order.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var foundOrder = order.get();
        var review = foundOrder.getOrderReview();
        if(review != null) {
            review.setReview(orderReview.getReview());
            review.setServiceRating(orderReview.getServiceRating());
            review.setDeliveryRating(orderReview.getDeliveryRating());
            iOrderReviewRepository.save(review);

        } else {
            var newReview = OrderReview.builder().deliveryRating(orderReview.getDeliveryRating()).serviceRating(orderReview.getServiceRating())
                    .review(orderReview.getReview()).order(foundOrder).build();
            iOrderReviewRepository.save(newReview);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public Long getOrderCount() {
        return iOrderRepository.count();
    }

}
