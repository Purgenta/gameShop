package com.purgenta.gameshop.services.review;

import com.purgenta.gameshop.models.game.Game;
import com.purgenta.gameshop.models.review.Review;
import com.purgenta.gameshop.models.user.User;
import com.purgenta.gameshop.repositories.IReviewRepository;
import com.purgenta.gameshop.requests.review.ReviewRequest;
import com.purgenta.gameshop.response.review.PageableReviews;
import com.purgenta.gameshop.response.review.ReviewEligibility;
import com.purgenta.gameshop.services.game.IGameService;
import com.purgenta.gameshop.services.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    private final int REVIEW_SIZE = 15;
    private final IReviewRepository iReviewRepository;
    private final IOrderService iOrderService;

    private final IGameService iGameService;

    @Override
    public ResponseEntity<?> getPageableReview(int game_id, int page) {
        var game = iGameService.findGameById(game_id);
        if (game.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Pageable pageable = PageRequest.of(page, REVIEW_SIZE,Sort.by("createdAt").descending());
        var reviews = iReviewRepository.findAllByGame(pageable, game.get());
        double count = (double) iReviewRepository.countAllByGame(game.get()) / REVIEW_SIZE;
        return new ResponseEntity<>(new PageableReviews((int) Math.ceil(count), reviews), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> checkReviewEligibility(int game_id) {
        var game = iGameService.findGameById(game_id);
        if (game.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var context = SecurityContextHolder.getContext().getAuthentication();
        if (context.getPrincipal() instanceof AnonymousAuthenticationToken)
            return new ResponseEntity<>(new ReviewEligibility(false), HttpStatus.UNAUTHORIZED);
        var user = (User) context.getPrincipal();
        return new ResponseEntity<>(new ReviewEligibility(checkUserReviewEligibility(user, game.get())), HttpStatus.OK);
    }

    private boolean checkUserReviewEligibility(User user, Game game) {
        if (iReviewRepository.findAllByUserAndGame(user, game).size() > 0) return false;
        return !iOrderService.findPlacedOrder(user, game);
    }

    @Override
    public ResponseEntity<?> addReview(ReviewRequest reviewRequest) {
        var game = iGameService.findGameById(reviewRequest.getGame_id());
        if (game.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!this.checkUserReviewEligibility(user, game.get())) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var review = Review.builder().rating(reviewRequest.getRating()).review(reviewRequest.getReview()).game(game.get()).user(user).
                createdAt(new Date(System.currentTimeMillis())).build();
        iReviewRepository.save(review);
        return  new ResponseEntity<>(HttpStatus.CREATED);

    }


}
