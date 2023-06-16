package com.purgenta.gameshop.services.review;

import com.purgenta.gameshop.models.game.Game;
import com.purgenta.gameshop.models.user.User;
import com.purgenta.gameshop.requests.review.ReviewRequest;
import org.springframework.http.ResponseEntity;

public interface IReviewService {
    ResponseEntity<?> getPageableReview(int game_id,int page);
    ResponseEntity<?> checkReviewEligibility(int game_id);
    ResponseEntity<?> addReview(ReviewRequest reviewRequest);
}
