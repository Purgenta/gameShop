package com.purgenta.gameshop.services.publisher;

import com.purgenta.gameshop.models.game.Publisher;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IPublisherService {

    List<Publisher> getPublishers();
    ResponseEntity<?> updatePublisher(Publisher publisher,int publisherId);
    ResponseEntity<?> deletePublisher(int publisherId);
    ResponseEntity<?> addPublisher(Publisher publisher);
}
