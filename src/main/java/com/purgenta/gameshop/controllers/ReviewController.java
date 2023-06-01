package com.purgenta.gameshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("review")
public class ReviewController {
    @PostMapping("addReview")
    public ResponseEntity<?> addReview() {
        return null;
    }
    @DeleteMapping("deleteReview/{review_id}")
    public ResponseEntity<?> deleteReview(@PathVariable int review_id) {
        return null;
    }
    @PutMapping("updateReview/{review_id}")
    public ResponseEntity<?> updateReview(@PathVariable int review_id) {
        return null;
    }
}
