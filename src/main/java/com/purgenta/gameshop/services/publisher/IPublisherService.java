package com.purgenta.gameshop.services.publisher;

import com.purgenta.gameshop.models.game.Publisher;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IPublisherService {
    Optional<Publisher> getPublisherById(int publisherId);

    List<Publisher> getPublishers();
    ResponseEntity<Map<String,String>> addPublisher(Publisher publisher);
}
