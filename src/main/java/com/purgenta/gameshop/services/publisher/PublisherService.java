package com.purgenta.gameshop.services.publisher;

import com.purgenta.gameshop.models.game.Publisher;
import com.purgenta.gameshop.repositories.IPublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublisherService implements IPublisherService {

    private final IPublisherRepository publisherRepository;


    @Override
    public List<Publisher> getPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public ResponseEntity<?> updatePublisher(Publisher publisher, int publisherId) {
        var foundPublisher = getPublisherById(publisherId);
        if(foundPublisher.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        publisher.setPublisher_id(publisherId);
        publisherRepository.save(publisher);
        return new ResponseEntity<>(publisher,HttpStatus.OK);
    }
    @Override
   public Optional<Publisher> getPublisherById(int publisherId) {
        return this.publisherRepository.findById(publisherId);
   }

    @Override
    public ResponseEntity<?> deletePublisher(int publisherId) {
        var foundPublisher = getPublisherById(publisherId);
        if(foundPublisher.isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        publisherRepository.delete(foundPublisher.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addPublisher(Publisher publisher) {
        publisherRepository.save(publisher);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
