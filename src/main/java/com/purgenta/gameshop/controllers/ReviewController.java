package com.purgenta.gameshop.controllers;

import com.purgenta.gameshop.requests.review.ReviewRequest;
import com.purgenta.gameshop.services.review.IReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("review")
@RequiredArgsConstructor
public class ReviewController {
    private final IReviewService iReviewService;
    @PostMapping("addReview")
    public ResponseEntity<?> addReview(@RequestBody @Valid ReviewRequest request) {
        return iReviewService.addReview(request);
    }
    @DeleteMapping("deleteReview/{review_id}")
    public ResponseEntity<?> deleteReview(@PathVariable int     review_id) {
        return null;
    }
    @GetMapping("getReviewEligibility/{game_id}")
    public ResponseEntity<?> getReviewEligibility(@PathVariable int game_id) {
        if(game_id < 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return iReviewService.checkReviewEligibility(game_id);
    }
    @PutMapping("updateReview/{review_id}")
    public ResponseEntity<?> updateReview(@PathVariable int review_id) {
        return null;
    }
    @GetMapping("/getReviews/{game_id}/{page}")
    public ResponseEntity<?> getReviews(@PathVariable int game_id, @PathVariable  int page) {
        if(page < 0 || game_id < 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return iReviewService.getPageableReview(game_id,page-1);
    }
}
