package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.game.Game;
import com.purgenta.gameshop.models.review.Review;
import com.purgenta.gameshop.models.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface IReviewRepository extends JpaRepository<Review,Long>, PagingAndSortingRepository<Review,Long> {
    List<Review> findAllByGame(Pageable pageable,Game game);
    List<Review> findAllByUserAndGame(User user, Game game);
    @Query(nativeQuery = true,value = "SELECT shop.review.game_id,AVG(shop.review.rating) as rating FROM shop.review GROUP BY shop.review.game_id ORDER BY rating DESC ")
    List<Integer> selectBestReviewedGames();
    Long countAllByGame(Game game);
}
