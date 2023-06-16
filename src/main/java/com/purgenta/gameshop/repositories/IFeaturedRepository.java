package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.game.Featured;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFeaturedRepository extends JpaRepository<Featured,Integer> {
}
