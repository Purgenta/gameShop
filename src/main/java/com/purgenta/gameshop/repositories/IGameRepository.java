package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;


public interface IGameRepository extends JpaRepository<Game, Integer>, JpaSpecificationExecutor<Game> {
    Optional<Game> findByTitle(String title);
    Page<Game> findAllByIsSelling (Boolean isSelling,Pageable pageable);

    Page<Game> findAll(Specification<Game> specification,Pageable pageable);
    Page<Game> findByIsSellingAndTitleContains(Boolean isSelling,String title, Pageable pageable);
}
