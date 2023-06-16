package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.order.OrderReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderReviewRepository extends JpaRepository<OrderReview,Integer> {
}
