package com.purgenta.gameshop.controllers;


import com.purgenta.gameshop.requests.order.OrderReviewRequest;
import com.purgenta.gameshop.services.order.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService iOrderService;
    @PostMapping("addReview/{id}")
    public ResponseEntity<?> addReview(@RequestBody @Valid OrderReviewRequest orderReview, @PathVariable int id) {
        return iOrderService.addReview(orderReview,id);
    }
    @DeleteMapping("deleteReview/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable int id) {
        return  iOrderService.deleteReview(id);
    }
}
