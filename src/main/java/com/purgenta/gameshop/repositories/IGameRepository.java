package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.game.Game;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface IGameRepository extends JpaRepository<Game, Integer>, JpaSpecificationExecutor<Game> {
    Optional<Game> findByTitle(String title);
    Optional<Game> findByIdAndSellingIsTrue(int id);

    @Query(value="SELECT MIN(price) AS minPrice, MAX(price) AS maxPrice,MIN(release_year) AS minYear,MAX(release_year) " +
            "AS maxYear FROM game",nativeQuery = true)
    Map<String,Double> findMinMax();
    @NotNull
    Page<Game> findAll(Specification<Game> specification, @NotNull Pageable pageable);

    List<Game> findAllBySellingIsTrue(Pageable pageable);
}
