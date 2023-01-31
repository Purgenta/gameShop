package com.purgenta.gameshop.services.publisher;

import com.purgenta.gameshop.models.Publisher;
import com.purgenta.gameshop.repositories.IPublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublisherService implements IPublisherService {

    private final IPublisherRepository publisherRepository;
    @Override
    public Optional<Publisher> getPublisherById(int publisherId) {
        return publisherRepository.findById(publisherId);
    }

    @Override
    public List<Publisher> getPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public ResponseEntity<Map<String, String>> addPublisher(Publisher publisher) {
        Map<String,String> response = new HashMap<>();
        response.put("success","Successfully created a new publisher");
        publisherRepository.save(publisher);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
