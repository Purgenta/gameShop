package com.purgenta.gameshop.repositories;

import com.purgenta.gameshop.models.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPublisherRepository extends JpaRepository<Publisher,Integer> {
    Optional<Publisher> findPublisherByName(String name);
}
